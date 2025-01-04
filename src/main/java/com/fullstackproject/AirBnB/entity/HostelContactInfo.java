package com.fullstackproject.AirBnB.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class HostelContactInfo {
    private String address;
    private String location;
    private String email;
    private String phoneNumber;
}
