package com.taxePro.Service;

import com.taxePro.Entity.Produit;
import org.springframework.stereotype.Service;

@Service
public interface TaxeService {
    double calculateTax(Produit produit);
}
