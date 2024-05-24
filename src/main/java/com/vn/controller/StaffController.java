package com.vn.controller;

import com.vn.dto.StaffDTO;
import com.vn.repository.StaffRepository;
import com.vn.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StaffController {
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    StaffService staffService;

    //    Create phase
    @GetMapping("/admin/create-staff")
    public String create(ModelMap modelMap) {
        modelMap.addAttribute("staffDTO", new StaffDTO());
        return "/admin/create-staff";
    }

    @PostMapping("/admin/create-staff")
    public String createPost(
            @ModelAttribute("staffDTO") StaffDTO staffDTO,
            BindingResult bindingResult,
            ModelMap modelMap,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "/admin/create-staff";
        }

        if (staffService.checkExistEmail(staffDTO.getEmail())) {
            modelMap.addAttribute("message", "This email already exists! Try another one!");
            return "/admin/create-staff";
        } else if (staffService.checkExistName(staffDTO.getName())) {
            modelMap.addAttribute("message", "Your name already exists! Try another one!");
            return "/admin/create-staff";
        } else {
            staffService.createCRUD(staffDTO);
            redirectAttributes.addFlashAttribute("existMessage", true);
            redirectAttributes.addFlashAttribute("message", "Create successfully!");
            return "redirect:/admin/view-all-staff";
        }
    }

    //-------------------------------------
//    View and Update phase
    @GetMapping("/admin/view-staff/{staffId}")
    public String showStaff(
            ModelMap modelMap
    ) {
        modelMap.addAttribute("staffViewDTO", new StaffDTO());
        return "admin/view-staff";
    }

    @PostMapping("/admin/view-staff/{staffId}")
    public String updateCurrentStaff(
            @ModelAttribute("staffViewDTO") StaffDTO staffViewDTO,
            RedirectAttributes redirectAttributes
    ) {
        staffService.updateCRUD(staffViewDTO);
        redirectAttributes.addFlashAttribute("existMessage", true);
        redirectAttributes.addFlashAttribute("message", "Update successfully!");

        return "redirect:/admin/view-all-staff";
    }

    //-------------------------------------
//      viewAll phase
    @GetMapping("/admin/view-all-staff")
    public String viewAll() {
        return "/admin/view-all-staff";
    }


    @GetMapping("/admin/view-claim-draft")
    public String pageAdminViewAllDraft() {
        return "/employee/view-claim-draft";
    }

    @GetMapping("/admin/view-claim-pending-approval")
    public String pageAdminViewAllPendingApproval() {
        return "/employee/view-claim-pending-approval";
    }

    @GetMapping("/admin/view-claim-approved")
    public String pageAdminViewAllApproved() {
        return "/employee/view-claim-approved";
    }

    @GetMapping("/admin/view-claim-paid")
    public String pageAdminViewAllPaid() {
        return "/employee/view-claim-paid";
    }

    @GetMapping("/admin/view-claim-cancelled")
    public String pageAdminViewAllCancelled() {
        return "/employee/view-claim-cancelled";
    }
    
}
