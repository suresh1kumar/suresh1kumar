package com.neom.fss.neompay.otp.repository;

import com.neom.fss.neompay.otp.repository.entity.BlockedCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BlockedCustomerRepository extends JpaRepository<BlockedCustomer, String> {



    @Query("select n from BlockedCustomer n where n.userIdentifier = ?1")
    BlockedCustomer findByIdentifier(String userIdentifier);
}
