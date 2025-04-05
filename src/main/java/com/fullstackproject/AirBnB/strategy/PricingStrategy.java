package com.fullstackproject.AirBnB.strategy;

import com.fullstackproject.AirBnB.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);
}
