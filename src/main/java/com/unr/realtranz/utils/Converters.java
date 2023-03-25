package com.unr.realtranz.utils;

import com.unr.realtranz.entities.Users;
import com.unr.realtranz.model.UserModel;
import com.unr.realtranz.repository.UserRepository;
import com.unr.realtranz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.Optional;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:18-01-2023 01:18
 **/
@Component
public class Converters {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public UserModel convertUsersEntityToUserModel(Users u){
        UserModel userModel = new UserModel(u.getId(),u.getFirstName(),u.getLastName(),u.getEmail_id(),u.getUsername(),u.getPhone1(),u.getPhone2(),u.getOccupation(),u.getIncome(),u.getNationality(),
                u.getAddressLine1(),u.getAddressLine2(),u.getCity(),u.getState(),u.getPinNumber());
        return userModel;
    }

    public Users convertAndSaveUserModelToUsersEntity(UserModel userModel){
        Optional<Users> optUser = userRepository.findById(userModel.getId());
        if(optUser.isEmpty()){
            throw new RuntimeException("User Not found for id: "+userModel.getId());
        }
        Users users = getUserEntity(userModel,optUser.get());
        return userRepository.save(users);

    }
    private Users getUserEntity(UserModel userModel , Users users){
        users.setAddressLine1(userModel.getAddressLine1());
        users.setAddressLine2(userModel.getAddressLine2());
        users.setCity(userModel.getCity());
        users.setState(userModel.getState());
        users.setPinNumber(userModel.getPinNumber());
        users.setNationality(userModel.getNationality());
        users.setEmail_id(userModel.getEmail_id());
        users.setPhone1(userModel.getPhone1());
        users.setPhone2(userModel.getPhone2());
        users.setOccupation(userModel.getOccupation());
        return users;
    }
    public void setUserDetailsToModelMap(ModelMap modelMap){

        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
            Users user = userService.getUsersByUserName(userName);
            if(null != user){
                UserModel userModel = convertUsersEntityToUserModel(user);
                if(null == modelMap){
                    modelMap = new ModelMap();
                }
                modelMap.addAttribute("userData",userModel);
            }
        }
    }
}
