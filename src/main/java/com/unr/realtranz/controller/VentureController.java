package com.unr.realtranz.controller;

import com.unr.realtranz.entities.Plot;
import com.unr.realtranz.entities.Users;
import com.unr.realtranz.entities.Venture;
import com.unr.realtranz.model.UserModel;
import com.unr.realtranz.repository.PlotRepository;
import com.unr.realtranz.repository.VentureRepository;
import com.unr.realtranz.service.*;
import com.unr.realtranz.utils.Converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:15-12-2022 00:20
 **/

@Controller
public class VentureController {

    @Autowired
    private VentureService ventureService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RoleService roleService;

    @Autowired
    private Converters converters;

    @RequestMapping(value="/venture",method = RequestMethod.POST)
    public ResponseEntity saveVenture(@RequestBody Venture venture){
        ventureService.saveVenture(venture);
        return new ResponseEntity<>("Created Venture",HttpStatus.CREATED);
    }

    @RequestMapping(value="/venture/{venture}",method = RequestMethod.GET)
    public Venture getVenture(@PathVariable("venture") String ventureName){
        return ventureService.getVentureByName(ventureName);
    }

    @RequestMapping(value="/plotstatus/{venture}",method = RequestMethod.GET)
    public ModelAndView getVentureStatus(@PathVariable("venture") String ventureName){
        ModelMap modelMap = new ModelMap("venture",ventureService.getVentureByName(ventureName));
        converters.setUserDetailsToModelMap(modelMap);
        ModelAndView modelAndView = new ModelAndView("layoutstatus",modelMap);
        return modelAndView;
    }


    @GetMapping({"/ventures","/","/page"})
    public ModelAndView getAllVentures(@Param("successEnq")String successEnq, @Param(value = "pageNo") Integer pageNo , ModelMap modelMap){
        if(null != successEnq && !successEnq.isEmpty()){
            modelMap.addAttribute("successEnq",successEnq);
        }
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !"anonymousUser".equalsIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName())){
            Long count = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().filter(r -> r.getAuthority().equalsIgnoreCase("ROLE_ADMIN")).count();
            String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
            Users user = userService.getUsersByUserName(userName);
            if(null != user){
                UserModel userModel = converters.convertUsersEntityToUserModel(user);
                modelMap.addAttribute("userData",userModel);
            }
            if(count > 0){
                List<Venture> ventures = ventureService.getAllVenturesByUser(user);
                modelMap.addAttribute("userVentures",ventures);
                ModelAndView modelAndView = new ModelAndView("dashboard",modelMap);
                return modelAndView;
            }else{
                if(null==pageNo){pageNo =1;}
                int pageSize = 5;if(pageNo ==0){pageNo=1;}
                Page< Venture > page = ventureService.findPaginated(pageNo, pageSize);
                List < Venture > ventures = page.getContent();

                modelMap.addAttribute("currentPage", pageNo);
                modelMap.addAttribute("totalPages", page.getTotalPages());
                modelMap.addAttribute("totalItems", page.getTotalElements());
                modelMap.addAttribute("ventures",ventures);
                ModelAndView modelAndView = new ModelAndView("searchresults",modelMap);
                return modelAndView;
            }
        }
        ModelAndView modelAndView = new ModelAndView("login",modelMap);
        return modelAndView;
    }

    @Autowired
    private VentureRepository ventureRepository;

    @Autowired
    private PlotRepository plotRepository;
    @GetMapping({"/availableplots/{venture}"})
    @Transactional
    public ModelAndView getAvailablePlotsByVenture(@PathVariable("venture") String venture, @Param("successEnq")String successEnq){


        List<Plot> plotList = new ArrayList<>();

        plotList = plotRepository.findByVenture(ventureRepository.findByVentureName(venture));

        ModelMap modelMap = new ModelMap("availablePlots",plotList);
        converters.setUserDetailsToModelMap(modelMap);
        if(null != successEnq){
            modelMap.addAttribute("successEnq",successEnq);
        }
        modelMap.addAttribute("ventureName",venture);
        modelMap.addAttribute("venture", ventureService.getVentureByName(venture));
        ModelAndView modelAndView = new ModelAndView("availableplots",modelMap);
        return modelAndView;
    }

}
