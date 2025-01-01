package com.mate.album30.domain.common;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class Account implements Serializable {
    private String bank;
    private String accountNumber;
    private String accountHolder;

    @Override
    public String toString() {
        return accountHolder + ", " + bank + ", " + accountNumber;
    }
}
