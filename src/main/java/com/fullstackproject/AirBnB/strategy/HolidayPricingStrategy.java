package com.fullstackproject.AirBnB.strategy;

import com.fullstackproject.AirBnB.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapper;
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = inventory.getRoom().getBasePrice();
        boolean isTodayHoliday = true;
        if(isTodayHoliday){
            price = price.multiply(BigDecimal.valueOf(1.4));
            return price;
        }

        return price;
    }
}
