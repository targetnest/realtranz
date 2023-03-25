package com.unr.realtranz.controller;

import com.unr.realtranz.Exception.InvalidOldPasswordException;
import com.unr.realtranz.entities.Users;
import com.unr.realtranz.entities.Venture;
import com.unr.realtranz.model.UserModel;
import com.unr.realtranz.service.UserService;
import com.unr.realtranz.service.VentureService;
import com.unr.realtranz.utils.Converters;
import com.unr.realtranz.utils.GenericResponse;
import com.unr.realtranz.utils.RealTranzConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:07-06-2022 01:34
 **/
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    VentureService ventureService;


    @Autowired
    private Converters converters;

    @GetMapping("/dashboard")
    public ModelAndView getDashboard(){
        String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.getUsersByUserName(userName);
        List<Venture> ventures = ventureService.getAllVenturesByUser(user);
        ModelMap modelMap = new ModelMap();
        converters.setUserDetailsToModelMap(modelMap);
        modelMap.addAttribute("userVentures",ventures);
        ModelAndView modelAndView = new ModelAndView("dashboard",modelMap);
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView getHome(){
        ModelAndView modelAndView = new ModelAndView("search");
        return modelAndView;
    }

    @RequestMapping(value="/updateUser", method = RequestMethod.POST)
    public ModelAndView updateUser(UserModel userModel,ModelMap modelMap) throws IOException {
        converters.setUserDetailsToModelMap(modelMap);
        converters.convertAndSaveUserModelToUsersEntity(userModel);
        modelMap.addAttribute("userAddSuccess","Profile Updated Successfully");
        ModelAndView modelAndView = new ModelAndView("redirect:/profile",modelMap);
        return modelAndView;
    }

    @RequestMapping(value="/profile", method = RequestMethod.GET)
    public ModelAndView getProfile(@Param("userAddSuccess") String userAddSuccess, ModelMap modelMap) throws IOException {
        converters.setUserDetailsToModelMap(modelMap);
        modelMap.addAttribute("userAddSuccess",userAddSuccess);
        ModelAndView modelAndView = new ModelAndView("profile",modelMap);
        return modelAndView;
    }

    @RequestMapping(value="/error", method = RequestMethod.GET)
    public ModelAndView errorPage(){
        ModelMap modelMap = new ModelMap();
        ModelAndView modelAndView = new ModelAndView("error",modelMap);
        return modelAndView;
    }
    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.GET)
    public GenericResponse changeUserPassword(@RequestParam("password") String password,
                                              @RequestParam("oldpassword") String oldPassword) {
        Users user = userService.getUsersByUserName(
                SecurityContextHolder.getContext().getAuthentication().getName());

        if (!userService.checkIfValidOldPassword(user, oldPassword)) {
            try {
                throw new InvalidOldPasswordException();
            } catch (InvalidOldPasswordException e) {
                throw new RuntimeException(e);
            }
        }
        userService.changeUserPassword(user, password);
        return new GenericResponse(RealTranzConstants.updatePasswordSuc);
    }

}
