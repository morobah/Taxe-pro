package com.taxePro.Controller;

import com.taxePro.Entity.Produit;
import com.taxePro.Entity.Recu;
import com.taxePro.Service.RecuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taxe")
public class TaxeVente {

    private RecuService recuService ;


    public TaxeVente(RecuService recuService) {
        this.recuService = recuService;
    }

    @PostMapping("/genererRecu")
    public String createRecu(@RequestBody String input){
        List<Produit>  produits = recuService.parseProduits(input);
        Recu  recu = recuService.generateRecu(produits);
        return recuService.formatRecu(recu);
    }

    @GetMapping("/afficherRecu1")
    public String afficherRecu1() {
        String input = "1 Book à 12.49\n1 Music CD à 14.99\n1 Chocolate Bar à 0.85";
        List<Produit> produits = recuService.parseProduits(input);
        Recu recu = recuService.generateRecu(produits);
        return recuService.formatRecu(recu);
    }

    @GetMapping("/afficherRecu2")
    public String afficherRecu2() {
        String input = "1 box of imported chocolates à 10.00\n1 imported bottle of perfume à 47.50";
        List<Produit> produits = recuService.parseProduits(input);
        Recu recu = recuService.generateRecu(produits);
        return recuService.formatRecu(recu);
    }

    @GetMapping("/afficherRecu3")
    public String afficherRecu3() {
        String input = "1 imported bottle of perfume à 27.99\n1 bottle of perfume à 18.99\n1 packet of headache pills à 9.75\n1 box of imported chocolates à 11.25";
        List<Produit> produits = recuService.parseProduits(input);
        Recu recu = recuService.generateRecu(produits);
        return recuService.formatRecu(recu);
    }
}
