package com.neom.fss.neompay.otp.repository;


import com.neom.fss.neompay.otp.repository.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MerchantRepository extends JpaRepository<Merchant,String> {

    @Query("select m from Merchant m where m.mobileNo=?1")
    Merchant findByMobileNo(String mobileNo);
}
