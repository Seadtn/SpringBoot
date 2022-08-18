package com.example.demo.controller;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserRole;
import com.example.demo.model.UserService;
import com.example.demo.model.Utilisateur;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {
	@Autowired
    private UserService userService; 
	private EmailValidator emailValidator = new EmailValidator();
	public Utilisateur register(RegistrationRequest request) throws AddressException, MessagingException {
	    boolean isValidEmail=emailValidator.test(request.getEmail());
	    if(!isValidEmail) {
	    	throw new IllegalStateException("email non valideé");
	    }
		return userService.singUpUser(
				new Utilisateur(
					request.getNom(),request.getPrenom(),
					request.getCin(),request.getTitre(),request.getDate_naissance(),request.getEmail()
					,request.getPassword(),request.getVille(),UserRole.USER,request.getInscrir()
						)
			  );
	}

}
