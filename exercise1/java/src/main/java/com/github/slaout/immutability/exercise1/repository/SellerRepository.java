package com.github.slaout.immutability.exercise1.repository;

import com.github.slaout.immutability.exercise1.domain.report.Seller;

import java.util.Optional;

public interface SellerRepository {

    Optional<Seller> getSeller(long id);

}
