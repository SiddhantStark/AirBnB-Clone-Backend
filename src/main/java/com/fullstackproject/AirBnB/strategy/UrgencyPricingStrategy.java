package com.fullstackproject.AirBnB.strategy;

import com.fullstackproject.AirBnB.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;


@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy {
    private final PricingStrategy wrapper;
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapper.calculatePrice(inventory);
        LocalDate today = LocalDate.now();
        if(inventory.getDate().isBefore(today) && inventory.getDate().isBefore(today.plusDays(7))){
            return price.multiply(BigDecimal.valueOf(1.5));
        }

        return price;
    }
}
