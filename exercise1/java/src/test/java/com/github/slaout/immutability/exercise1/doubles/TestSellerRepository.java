package com.github.slaout.immutability.exercise1.doubles;

import com.github.slaout.immutability.exercise1.domain.report.Seller;
import com.github.slaout.immutability.exercise1.repository.SellerRepository;

import java.util.Optional;

public class TestSellerRepository implements SellerRepository {

    public Optional<Seller> getSeller(long id) {
        return Optional.of(new Seller(id, "Some seller"));
    }

}
