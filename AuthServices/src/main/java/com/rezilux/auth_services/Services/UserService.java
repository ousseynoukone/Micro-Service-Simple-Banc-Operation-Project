package com.rezilux.auth_services.Services ;

import com.rezilux.auth_services.Exception.AppException;
import com.rezilux.auth_services.Models.User;
import com.rezilux.auth_services.Repository.*;
import com.rezilux.auth_services.dtos.CredentialsDto;
import com.rezilux.auth_services.dtos.SignUpDto;
import com.rezilux.auth_services.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rezilux.auth_services.Mappers.UserMapper;
import java.nio.CharBuffer;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    //Autowired est utilisé pour "forcer" l'injection du dependance spécifié (en injectant une instance de la classe ?)
    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private UserMapper userMapper;
    public UserDto login(CredentialsDto credentialsDto){
        System.out.println("----------------------------------------------------------"+credentialsDto.login());
     User u =    userRepository.findByLogin(credentialsDto.login()).orElseThrow(()->new AppException("Utilisateur introuvable" , HttpStatus.NOT_FOUND));
    if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),u.getPassword())){
        return userMapper.toUserDto(u);

    }else{
        throw new AppException("Mots de passe incorrecte",HttpStatus.BAD_REQUEST) ;
    }

    }

    public UserDto register(SignUpDto signUpDto){
      Optional<User> oUser =  userRepository.findByLogin(signUpDto.login());
      if(oUser.isPresent()){
          throw new AppException("L'email saisit existe deja ! ", HttpStatus.BAD_REQUEST);

      }
      User u = userMapper.signUpToUser(signUpDto);
      u.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
      User registredUser = userRepository.save(u);

      return userMapper.toUserDto(registredUser);


    }

}
