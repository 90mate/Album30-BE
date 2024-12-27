package com.mate.album30.domain.common;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class Address implements Serializable {
    private String city;
    private String street;
    private String zipcode;
}