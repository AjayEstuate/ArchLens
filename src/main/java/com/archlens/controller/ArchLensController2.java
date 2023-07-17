package com.archlens.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archlens.entity.ExternalTableDataSource;



@Controller
public class ArchLensController2 {
	@RequestMapping("/")
	public String home() {
		System.out.println("Home method Running");
		return "home";
	}
	
	
	@PostMapping( "/register")
	public String addCandidate(@RequestBody ExternalTableDataSource ds ) {
	System.out.println("Registering ");
	System.out.println("Host"+ ds.getHost());
	System.out.println("Port"+ ds.getPort());
	System.out.println("User Name "+ ds.getUserName());
		return "register";
	}

}
