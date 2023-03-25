package com.unr.realtranz.repository;

import com.unr.realtranz.entities.Plot;
import com.unr.realtranz.entities.Users;
import com.unr.realtranz.entities.Venture;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:15-12-2022 00:21
 **/
@Repository
@Transactional
public interface VentureRepository extends JpaRepository<Venture,Long>  {

    Venture findByVentureName(String ventureName);

    List<Venture> findByOwner(Users users);

}
