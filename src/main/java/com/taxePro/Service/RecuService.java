package com.taxePro.Service;

import com.taxePro.Entity.Produit;
import com.taxePro.Entity.Recu;

import java.util.List;


public interface RecuService {
    Recu generateRecu(List<Produit> produits);
    Recu getRecuById(Long id);
    List<Produit> parseProduits(String input);
    String formatRecu(Recu recu);
}
