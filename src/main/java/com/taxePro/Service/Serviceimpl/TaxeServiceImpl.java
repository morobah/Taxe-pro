package com.taxePro.Service.Serviceimpl;

import com.taxePro.Entity.ProductType;
import com.taxePro.Entity.Produit;
import com.taxePro.Service.TaxeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TaxeServiceImpl implements TaxeService {

    private static final double BASIC_SALES_TAX_RATE = 0.10;
    private static final double IMPORT_DUTY_RATE = 0.05;

    @Override
    public double calculateTax(Produit produit) {
        double taxe = 0 ;
        if (produit.getType() == ProductType.OTHER) {
            taxe += produit.getPrice() * BASIC_SALES_TAX_RATE;
        }
        if (produit.getImported()) {
            taxe += produit.getPrice() * IMPORT_DUTY_RATE;
        }

        return roundToNearestFiveCents(taxe);
    }

    private double roundToNearestFiveCents(double amount) {
        return BigDecimal.valueOf(Math.ceil(amount * 20) / 20.0).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
