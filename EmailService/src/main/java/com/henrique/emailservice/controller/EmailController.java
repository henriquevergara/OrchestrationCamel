package com.henrique.emailservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {


    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody String json){

        System.out.println("Pingou aqui. " + json);
        return ResponseEntity.ok().body(json);

    }



}
