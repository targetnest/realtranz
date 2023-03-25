package com.unr.realtranz.repository;

import com.unr.realtranz.entities.Enquiry;
import com.unr.realtranz.entities.Venture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:15-12-2022 00:21
 **/
@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry,Long>  {

    List<Enquiry> findAllByVenture(String venture);
    List<Enquiry> findByCreatedBy(String userName);
}
