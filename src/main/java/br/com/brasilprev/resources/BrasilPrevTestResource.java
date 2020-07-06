package br.com.brasilprev.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrasilPrevTestResource {
		
		@RequestMapping("/")
	    public String home(){
	        return "Bem Vindo a BrasilPrev";
	    }
	

}
