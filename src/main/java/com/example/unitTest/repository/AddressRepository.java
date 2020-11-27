package com.example.unitTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.unitTest.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
