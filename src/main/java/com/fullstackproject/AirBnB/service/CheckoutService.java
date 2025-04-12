package com.fullstackproject.AirBnB.service;

import com.fullstackproject.AirBnB.entity.Booking;

public interface CheckoutService {
    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
}
