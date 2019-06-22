package tn.iset.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.iset.message.response.ResponseMessage;
import tn.iset.model.User;
import tn.iset.repository.UserRepository;
@CrossOrigin("*")
@RestController
@RequestMapping("mail")
public class MailController {
	 @Autowired
	    public JavaMailSender emailSender;
	 @Autowired
	 public UserRepository userRepo;
	 @Autowired
	 PasswordEncoder encoder;
	
	    @ResponseBody
	    @RequestMapping("/send/{username}")
	    public ResponseEntity<Object> sendSimpleEmail(@PathVariable String username) throws NoSuchAlgorithmException {
	    	User u=userRepo.findByUsername(username).get();
	    	Random random =  SecureRandom.getInstanceStrong(); 
	    	String generatePin = String.format("%04d", random.nextInt(10000));
	    	u.setPassword(encoder.encode(username+generatePin));
	    	userRepo.save(u);
	        SimpleMailMessage message = new SimpleMailMessage();
	         
	        message.setTo(u.getEmail());
	        message.setSubject("Récupuration du mot de passe");
	        message.setText("Bonjour,votre mot de passe est: "+username+generatePin);
	 
	        this.emailSender.send(message);
	 
	        return  new ResponseEntity<Object>(new ResponseMessage("email sent successfully!"), HttpStatus.OK);
	    }
}
