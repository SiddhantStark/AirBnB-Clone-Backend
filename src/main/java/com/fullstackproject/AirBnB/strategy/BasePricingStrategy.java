package com.fullstackproject.AirBnB.strategy;

import com.fullstackproject.AirBnB.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
public class BasePricingStrategy implements PricingStrategy{

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
