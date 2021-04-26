package com.henrique.controller;

import com.henrique.model.Usuario;
import com.henrique.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onboard")
public class OnboardController {

    @Autowired
    private UserService service;

    @PostMapping
    private ResponseEntity<?> listOnboard(@RequestBody Usuario usuario){

       try {
           System.out.println("Fui invocado!!!!  " + usuario.getNome() + " " + usuario.getDocumento());
            return service.createUser(usuario);
       }catch (Exception e){
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }

}
