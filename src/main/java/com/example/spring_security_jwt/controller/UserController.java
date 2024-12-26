package com.example.spring_security_jwt.controller;

import com.example.spring_security_jwt.dto.AuthRequest;
import com.example.spring_security_jwt.entity.Event;
import com.example.spring_security_jwt.entity.User;
import com.example.spring_security_jwt.service.EventService;
import com.example.spring_security_jwt.service.JwtService;
import com.example.spring_security_jwt.service.Userservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private Userservice service;
    @Autowired
    private EventService eventService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/adduser")
    public String addNewUser(@RequestBody @Valid User user){
        return service.addUser(user);
    }
    @PostMapping("/addEvent")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Event addEvent(@RequestBody Event job){
        return eventService.addEvent(job);
    }

    @GetMapping("/getallEvents")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Event> getallEvents(){
        return eventService.getAllEvents();
    }

    //login endpoint
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }



}
