package com.taxePro.Service.Serviceimpl;

import com.taxePro.Entity.ProductType;
import com.taxePro.Entity.Produit;
import com.taxePro.Entity.Recu;
import com.taxePro.Repository.ProduitRepository;
import com.taxePro.Repository.RecuRepository;
import com.taxePro.Service.TaxeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RecuServiceImplTest {

    private TaxeService taxeService;
    private RecuServiceImpl recuService;
    private RecuRepository recuRepository;
    private ProduitRepository produitRepository;

    @BeforeEach
    void setUp() {
        taxeService = new TaxeServiceImpl();
        recuRepository = Mockito.mock(RecuRepository.class);
        produitRepository = Mockito.mock(ProduitRepository.class);
        recuService = new RecuServiceImpl(taxeService, recuRepository, produitRepository);
    }

    @Test
    void testGenerateRecu() {
        Produit book = new Produit();
        book.setName("Book");
        book.setPrice(12.49);
        book.setType(ProductType.BOOK);
        book.setImported(false);

        Produit musicCD = new Produit();
        musicCD.setName("Music CD");
        musicCD.setPrice(14.99);
        musicCD.setType(ProductType.OTHER);
        musicCD.setImported(false);

        Produit chocolateBar = new Produit();
        chocolateBar.setName("Chocolate Bar");
        chocolateBar.setPrice(0.85);
        chocolateBar.setType(ProductType.FOOD);
        chocolateBar.setImported(false);

        List<Produit> produits = Arrays.asList(book, musicCD, chocolateBar);

        // Mock pour enregistrer les Recu
        when(produitRepository.save(any(Produit.class))).thenAnswer(i -> i.getArguments()[0]);
        when(recuRepository.save(any(Recu.class))).thenAnswer(i -> i.getArguments()[0]);

        Recu recu = recuService.generateRecu(produits);

        assertEquals(1.50, recu.getTotalTaxes(), 0.01);
        assertEquals(29.83, recu.getTotalPrice(), 0.01);
        assertEquals(3, recu.getRecuProduits().size());
    }

    @Test
    void parseProduits() {
        String input = "1 Book à 12.49\n1 Music CD à 14.99\n1 Chocolate Bar à 0.85";
        List<Produit> produits = recuService.parseProduits(input);
        assertEquals(3, produits.size());
        assertEquals("Book", produits.get(0).getName());
    }

    @Test
    void testformatRecu() {
        Produit book = new Produit();
        book.setName("Book");
        book.setPrice(12.49);
        book.setType(ProductType.BOOK);
        book.setImported(false);

        Produit musicCD = new Produit();
        musicCD.setName("Music CD");
        musicCD.setPrice(14.99);
        musicCD.setType(ProductType.OTHER);
        musicCD.setImported(false);

        Produit chocolateBar = new Produit();
        chocolateBar.setName("Chocolate Bar");
        chocolateBar.setPrice(0.85);
        chocolateBar.setType(ProductType.FOOD);
        chocolateBar.setImported(false);

        List<Produit> produits = Arrays.asList(book, musicCD, chocolateBar);

        when(produitRepository.save(any(Produit.class))).thenAnswer(i -> i.getArguments()[0]);
        when(recuRepository.save(any(Recu.class))).thenAnswer(i -> {
            Recu recu = (Recu) i.getArguments()[0];
            recu.setId(1L);
            return recu;
        });

        // Génération du reçu et formatage de la sortie
        Recu recu = recuService.generateRecu(produits);
        String output = recuService.formatRecu(recu);

        System.out.println("Output:\n" + output);

        assertTrue(output.contains("1 Book : 12.49"), "Expected to find '1 Book : 12.49' but got: " + output);
        assertTrue(output.contains("1 Music CD : 16.49"), "Expected to find '1 Music CD : 16.49' but got: " + output);
        assertTrue(output.contains("1 Chocolate Bar : 0.85"), "Expected to find '1 Chocolate Bar : 0.85' but got: " + output);
        assertTrue(output.contains("Sales Taxes: 1.50"), "Expected to find 'Sales Taxes: 1.50' but got: " + output);
        assertTrue(output.contains("Total: 29.83"), "Expected to find 'Total: 29.83' but got: " + output);
    }
}
