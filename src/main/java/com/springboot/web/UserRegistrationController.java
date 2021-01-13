package com.springboot.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.dao.ElementRepository;
import com.springboot.dao.EtablissementRepository;
import com.springboot.dao.FiliereRepository;
import com.springboot.dao.ModuleRepository;
import com.springboot.dao.ProfesseurRepository;
import com.springboot.dao.RoleRepository;
import com.springboot.entities.*;
import com.springboot.entities.Module;
import com.springboot.service.*;
import com.springboot.web.*;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProfesseurRepository professeurRepository;
    @Autowired
    private EtablissementRepository etablissementRepository;
    @Autowired
    private FiliereRepository filiereRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ElementRepository elementRepository;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
    	 List<Role> Role = roleRepository.findAll();
         model.addAttribute("Role", Role);
         List<Filiere> filiere = filiereRepository.findAll();
 	    model.addAttribute("Filiere", filiere);
 	   List<Module> module = moduleRepository.findAll();
	    model.addAttribute("Module", module);
	    List<Element> element = elementRepository.findAll();
	    model.addAttribute("Element", element);
	    
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result,Model model){
    	 List<Role> Role = roleRepository.findAll();
         model.addAttribute("Role", Role);
        User existing = userService.findByEmail(userDto.getEmail());
      
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "registration";
        }
        String role=userDto.getRole();
		  if(role.equals("0")) { Professeur p=new Professeur();
		  p.setNom(userDto.getFirstName()); p.setPrenom(userDto.getLastName());
		  p.setEtablissement(etablissementRepository.findById(1L).get());
		   professeurRepository.save(p); }
		 
        userService.save(userDto);
        
        return "redirect:/registration?success";
    }

}