package com.unr.realtranz.controller;

import com.unr.realtranz.entities.Enquiry;
import com.unr.realtranz.entities.Plot;
import com.unr.realtranz.entities.Venture;
import com.unr.realtranz.models.PlotStatus;
import com.unr.realtranz.repository.EnquiryRepository;
import com.unr.realtranz.service.EnquiryService;
import com.unr.realtranz.service.VentureService;
import com.unr.realtranz.utils.Converters;
import com.unr.realtranz.utils.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:17-12-2022 22:27
 **/

@RestController
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private VentureService ventureService;

    @Autowired
    private UserValidations userValidations;

    @Autowired
    private Converters converters;

    @RequestMapping(value="/enquiry", method = RequestMethod.POST)
    public ModelAndView saveEnquiry( Enquiry enquiry, ModelMap model){
        Enquiry enq = enquiryService.saveEnquiry(enquiry);
        model.addAttribute("successEnq","Your Enquiry Submitted Successfully For PlotID:: <b>"+enq.getDetails().split(":::")[1]+"</b>");
        ModelAndView redirectView = new ModelAndView("redirect:/availableplots/"+enq.getVenture(),model);
        return redirectView;
    }

    @RequestMapping(value="/close-enquiry", method = RequestMethod.POST)
    public ModelAndView closeEnquiry( Enquiry enquiry, ModelMap model){
        Optional<Enquiry> optEnq = enquiryRepository.findById(enquiry.getId());
        Enquiry enq = optEnq.get();
        if(userValidations.ventureAndUserRequestIsValidWithAdmin(enquiry.getVenture(),SecurityContextHolder.getContext().getAuthentication().getName()) && null != enq && !enq.isEnquiryClosed()){
            enq.setEnquiryClosed(true);
            enq.setClosedComments(enquiry.getClosedComments());
            Enquiry savedEnq = enquiryRepository.save(enq);
            model.addAttribute("successEnq","Enquiry Updated Successfully For PlotID:: <b>"+enq.getDetails().split(":::")[1]+"</b>");
            ModelAndView redirectView = new ModelAndView("redirect:/",model);
            return redirectView;
        }
        throw new RuntimeException("Request is not valid/ Invalid Access");
    }

    @RequestMapping(value="/enquiries/user/{userName}")
    public List<Enquiry> getEnquiriesByUser(@PathVariable("userName") String userName){
        if(null == userName || !userName.equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new RuntimeException("Request is not valid/ Invalid Access");
        }
        return enquiryRepository.findByCreatedBy(userName);
    }

    @RequestMapping(value="/enquiry/plot", method = RequestMethod.GET)
    public String getEnquiry(@ModelAttribute("enquiryObj") Enquiry enquiry,Model model){
        return "Enquiry Created";
    }

    @Autowired
    EnquiryRepository enquiryRepository;
    @RequestMapping(value="/enquiries", method = RequestMethod.GET)
    public List<Enquiry> getEnquiries(){
        return enquiryRepository.findAll();
    }

    @RequestMapping(value="/enquiries/venture/{ventureName}/enquiry/{id}", method = RequestMethod.GET)
    public ModelAndView getEnquiryById(@PathVariable("id") Long id,@PathVariable("ventureName") String ventureName){
        if(!userValidations.ventureAndUserRequestIsValid(ventureName,SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new RuntimeException("Request is not valid/ Invalid Access");
        }
        Optional<Enquiry> enquiry = enquiryRepository.findById(id);
        if(enquiry.isEmpty()){
            throw new RuntimeException("Requested Enquiry Not Available/ Invalid Access");
        }

        ModelMap modelMap = new ModelMap("enquiryObj",enquiry.get());
        converters.setUserDetailsToModelMap(modelMap);
        ModelAndView modelAndView = new ModelAndView("close-enquiry",modelMap);
        return modelAndView;
    }


    @RequestMapping(value="/enquiry/{venture}", method = RequestMethod.GET)
    public List<Plot> getEnquiryForm(@PathVariable("venture") String venture, Model model){
        List<Plot> plotList = new ArrayList<>();
        if (null != ventureService.getVentureByName(venture).getPlotList()){
            plotList = ventureService.getVentureByName(venture).getPlotList().stream().filter(p -> p.getPltStatus() == PlotStatus.AVAILABLE).collect(Collectors.toList());
        }
        model.addAttribute("availablePlots",plotList);
        model.addAttribute("ventureName",venture);
        return plotList;
    }

    @RequestMapping(value="/enquiry", method = RequestMethod.GET)
    public List<Enquiry> getEnquiries(@Param("venture") String venture){
        return enquiryRepository.findAllByVenture(venture);
    }
}
