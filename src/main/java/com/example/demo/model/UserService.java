package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.controller.token.ConfirmationToken;
import com.example.demo.controller.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
	private final static String User_Not_Found_MSG="L'Utilisateur avec l'mail"
			+ "%s n'existe pas!";
   @Autowired
   private UserRepository userRepository;
   private BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
   @Autowired
   private  ConfirmationTokenService confirmationTokenService;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
				.orElseThrow(()->new UsernameNotFoundException(String.format(User_Not_Found_MSG,email)));
	}
	
    public Utilisateur singUpUser(Utilisateur user) throws AddressException, MessagingException {
    	boolean userEmail=userRepository.findByEmail(user.getEmail()).isPresent();
    	boolean userCin=userRepository.findByCin(user.getCin()).isPresent();
		if(userEmail) {
			throw new IllegalStateException("email deja utilisé");
		}else if(userCin) {
			throw new IllegalStateException("Numero de cin deja utilisé");
		}
		String encodedPass=bCryptPasswordEncoder.encode(user.getPassword());
		  user.setPassword(encodedPass);
		  String token = UUID.randomUUID().toString();
		  ConfirmationToken confirmationToken = new ConfirmationToken(
	                token,
	                LocalDateTime.now(),
	                LocalDateTime.now().plusMinutes(15),
	                user
	        );
            
	        confirmationTokenService.saveConfirmationToken(
	        		confirmationToken);
	        
    	return userRepository.save(user);
    	
    }
}
