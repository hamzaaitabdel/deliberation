package com.springboot.service.imple;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.entities.*;
import com.springboot.entities.Module;
import com.springboot.service.UserService;
import com.springboot.dao.ElementRepository;
import com.springboot.dao.EtablissementRepository;
import com.springboot.dao.FiliereRepository;
import com.springboot.dao.ModuleRepository;
import com.springboot.dao.ProfesseurRepository;
import com.springboot.dao.RoleRepository;
import com.springboot.dao.UserRepository;
import com.springboot.dao.chefdefiliereRepository;
import  com.springboot.web.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EtablissementRepository etablissementRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ElementRepository elementRepository;
    @Autowired
    private chefdefiliereRepository chefdefiliereRepository ;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(UserRegistrationDto registration){
        User user = new User();
        String role=registration.getRole();
        Role r=  roleRepository.findById_Role(Long.parseLong(role) );
		/*
		 * Filiere f=
		 * filiereRepository.findById_filiere(Long.parseLong(registration.getFiliere()))
		 * ; Module m=
		 * moduleRepository.findByid(Long.parseLong(registration.getModule())); Element
		 * e= elementRepository.findById_Element(Long.parseLong(
		 * registration.getElement()));
		 */
        
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setCin(registration.getCin());
        user.setTelephone(registration.getTelephone());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
       user.setRoles(Arrays.asList(r));
       user.setProfesseur(professeurRepository.findByNom(registration.getFirstName(),registration.getLastName()));
       user.setChef_de_Filiere(chefdefiliereRepository.findByNom(registration.getFirstName(),registration.getLastName()));
      
        return userRepository.save(user);
        
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
