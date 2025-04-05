package com.fullstackproject.AirBnB.strategy;

import com.fullstackproject.AirBnB.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapper;
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapper.calculatePrice(inventory);
        double occupancyPrice = (double) inventory.getBookedCount()/inventory.getTotalCount();

        if(occupancyPrice > 0.8){
            return price.multiply(BigDecimal.valueOf(1.2));
        }

        return price;
    }
}
