package com.taxePro.Service.Serviceimpl;

import com.taxePro.Entity.ProductType;
import com.taxePro.Entity.Produit;
import com.taxePro.Entity.Recu;
import com.taxePro.Entity.RecuProduit;
import com.taxePro.Repository.ProduitRepository;
import com.taxePro.Repository.RecuRepository;
import com.taxePro.Service.RecuService;
import com.taxePro.Service.TaxeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RecuServiceImpl implements RecuService {

    private final TaxeService taxeService;
    private final RecuRepository recuRepository;
    private final ProduitRepository produitRepository;
    private static final Logger logger = Logger.getLogger(RecuServiceImpl.class.getName());


    public RecuServiceImpl(TaxeService taxeService, RecuRepository recuRepository, ProduitRepository produitRepository) {
        this.taxeService = taxeService;
        this.recuRepository = recuRepository;
        this.produitRepository = produitRepository;
    }

    // Génère un reçu basé sur une liste de produits
    @Override
    public Recu generateRecu(List<Produit> produits) {
        List<RecuProduit> recuProduits = new ArrayList<>();
        double totalSalesTaxes = 0;
        double totalPrice = 0;

        // calcul les taxes et les produits pour chaques produits
        for (Produit produit : produits) {
            produit = produitRepository.save(produit);
            double taxe = taxeService.calculateTax(produit);
            double prixavecTax = produit.getPrice() + taxe;
            totalSalesTaxes += taxe;
            totalPrice += prixavecTax;

            RecuProduit recuProduit = new RecuProduit();
            recuProduit.setProduit(produit);
            recuProduit.setQuantity(1);
            recuProduit.setPrice(prixavecTax);
            recuProduit.setSalesTax(taxe);
            recuProduits.add(recuProduit);
        }
        Recu recu = new Recu();
        recu.setRecuProduits(recuProduits);
        recu.setTotalTaxes(totalSalesTaxes);
        recu.setTotalPrice(totalPrice);
        recu.setPucharseTime(LocalDateTime.now());

        Recu savedRecu = recuRepository.save(recu);
        logger.info("Reçu enregistré avec succès: " + savedRecu);

        for (RecuProduit recuProduit : recuProduits) {
            recuProduit.setRecu(savedRecu);
        }
        return savedRecu;
    }

    // Récupère un reçu par son ID
    @Override
    public Recu getRecuById(Long id) {
        return recuRepository.findById(id).orElseThrow(() -> new RuntimeException("Reçu not Found"));
    }

    @Override
    public List<Produit> parseProduits(String input) {
        List<Produit> produits = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\d+)\\s+([^\\d]+)\\s+à\\s+(\\d+\\.\\d{2})");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            int quantity = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2).trim();
            double price = Double.parseDouble(matcher.group(3));

            Produit produit = new Produit();
            produit.setName(name);
            produit.setPrice(price);
            produit.setImported(name.toLowerCase().contains("imported"));
            produit.setType(determineProductType(name));

            // Ajoute le produit à la liste
            for (int i = 0; i < quantity; i++) {
                produits.add(produit);
            }
        }
        return produits;
    }

    // Détermine le type de produit en fonction de son nom
    private ProductType determineProductType(String name){
        if(name.toLowerCase().contains("book")){
            return ProductType.BOOK;
        } else if(name.toLowerCase().contains("chocolates") || name.toLowerCase().contains("food")){
            return ProductType.FOOD;
        } else if (name.toLowerCase().contains("medical") || name.toLowerCase().contains("pills")) {
            return ProductType.MEDICAL;
        } else {
            return ProductType.OTHER;
        }
    }

    // Formate le reçu pour l'affichage
    @Override
    public String formatRecu(Recu recu) {
        StringBuilder sb = new StringBuilder();
        Locale locale = Locale.US;
        for (RecuProduit recuProduit : recu.getRecuProduits()) {
            sb.append(String.format(locale, "%d %s : %.2f%n", recuProduit.getQuantity(), recuProduit.getProduit().getName(), recuProduit.getPrice()));
        }
        sb.append(String.format(locale, "Sales Taxes: %.2f%n", recu.getTotalTaxes()));
        sb.append(String.format(locale, "Total: %.2f%n", recu.getTotalPrice()));
        return sb.toString();
    }
}
