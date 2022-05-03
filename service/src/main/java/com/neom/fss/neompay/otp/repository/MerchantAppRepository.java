package com.neom.fss.neompay.otp.repository;



import com.neom.fss.neompay.otp.repository.entity.MerchantApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MerchantAppRepository extends JpaRepository<MerchantApplication,String> {

    @Query("select m from MerchantApplication m where m.mobileNo=?1")
    MerchantApplication findByMobileNo(String mobileNo);
}
