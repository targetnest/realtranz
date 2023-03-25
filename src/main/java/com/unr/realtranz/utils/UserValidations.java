package com.unr.realtranz.utils;

import com.unr.realtranz.entities.Users;
import com.unr.realtranz.entities.Venture;
import com.unr.realtranz.service.VentureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:24-01-2023 01:20
 **/

@Component
public class UserValidations {

    @Autowired
    private VentureService ventureService;

    public boolean ventureAndUserRequestIsValid(String ventureName, String name) {
        Venture venture = ventureService.getVentureByName(ventureName);
        if(null != venture){
            Users users = venture.getOwner();
            if(null==users)return false;
            if(users.getUsername().equalsIgnoreCase(name)){
                return true;
            }
        }

        return false;
    }

    public boolean ventureAndUserRequestIsValidWithAdmin(String venture, String name) {
        Venture venture1 = ventureService.getVentureByName(venture);
        if(null != venture1){
            Users users = venture1.getOwner();
            if(null==users)return false;
            Long count = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().filter(r -> r.getAuthority().equalsIgnoreCase("ROLE_ADMIN")).count();
            if(users.getUsername().equalsIgnoreCase(name) && count>0){
                return true;
            }
        }

        return false;
    }
}
