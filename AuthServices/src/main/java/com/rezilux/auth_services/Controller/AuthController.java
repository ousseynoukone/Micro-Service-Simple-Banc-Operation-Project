package com.rezilux.auth_services.Controller ;


import com.rezilux.auth_services.Config.UserAuthProvider;
import com.rezilux.auth_services.Services.UserService;
import com.rezilux.auth_services.dtos.CredentialsDto;
import com.rezilux.auth_services.dtos.SignUpDto;
import com.rezilux.auth_services.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor

public class AuthController {
    private final UserService userService;
    private  final UserAuthProvider userAuthProvider;

    @PostMapping("/auth/login")
        public ResponseEntity<?> login(@RequestBody CredentialsDto crendentialsDto){
        System.out.println(crendentialsDto.login());
        if(crendentialsDto.login()==null ||  crendentialsDto.password()==null){
            return ResponseEntity.badRequest().body("Vérifez que tout les champs sont bien remplit : *login , *password");


        }
        UserDto  user  = userService.login(crendentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);

        }
        @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody SignUpDto signUpDto){
            if(signUpDto.login()==null || signUpDto.password()==null || signUpDto.firstName()==null || signUpDto.lastName()==null) {
                return ResponseEntity.badRequest().body("Vérifez que tout les champs sont bien remplit : *login , *password , *firstname , *lastname");


            }else{
                UserDto user =   userService.register(signUpDto);
                user.setToken(userAuthProvider.createToken(user));
                return ResponseEntity.created(URI.create("/users/"+user.getId())).body(user);

            }



        }




    @PostMapping("/auth/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            // Extract the token from the Authorization header
            String token = authorizationHeader.substring("Bearer ".length()).trim();

            // Delegate token validation to userAuthProvider
            return ResponseEntity.ok().body(userAuthProvider.validateToken(token));
        } catch (Exception e) {
            // Handle validation failure, e.g., invalid token format
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
        }
    }


}
