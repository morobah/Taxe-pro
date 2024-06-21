package com.taxePro.Controller;

import com.taxePro.Entity.Produit;
import com.taxePro.Entity.Recu;
import com.taxePro.Service.RecuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/taxe")
public class TaxeVente {

    private RecuService recuService ;

    @Autowired
    public TaxeVente(RecuService recuService) {
        this.recuService = recuService;
    }

    @PostMapping("/genererRecu")
    public String createRecu(@RequestBody String input){
        List<Produit>  produits = recuService.parseProduits(input);
        Recu  recu = recuService.generateRecu(produits);
        return recuService.formatRecu(recu);
    }
}
