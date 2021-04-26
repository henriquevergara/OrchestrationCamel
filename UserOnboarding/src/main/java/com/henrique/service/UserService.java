package com.henrique.service;

import com.henrique.model.Usuario;
import com.henrique.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UsuarioRepository usuarioRepository;


    public ResponseEntity<?> createUser(Usuario usuario){

        if (Objects.isNull(usuario)){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
        }

    }

}
