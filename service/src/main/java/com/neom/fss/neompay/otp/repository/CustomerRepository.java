package com.neom.fss.neompay.otp.repository;

import com.neom.fss.neompay.otp.repository.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {


    @Query("select c from Customer c where c.mobileNo=?1")
    Customer findByMobileNo(String mobileNo);
}
