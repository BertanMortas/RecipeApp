package com.bilgeadam.repository;

import com.bilgeadam.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByAuthId(Long authId);
}
