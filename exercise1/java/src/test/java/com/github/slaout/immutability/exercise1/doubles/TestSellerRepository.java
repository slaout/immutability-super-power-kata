package com.github.slaout.immutability.exercise1.doubles;

import com.github.slaout.immutability.exercise1.domain.report.Seller;
import com.github.slaout.immutability.exercise1.repository.SellerRepository;
import lombok.Setter;

import java.util.Optional;

public class TestSellerRepository implements SellerRepository {

    @Setter
    private Seller knownSeller;

    public Optional<Seller> getSeller(long id) {
        if (knownSeller == null || knownSeller.getId() != id) {
            return Optional.empty();
        } else {
            return Optional.of(knownSeller);
        }
    }

}
