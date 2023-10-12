package com.rezilux.auth_services.Config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rezilux.auth_services.Exception.AppException;
import com.rezilux.auth_services.dtos.UserDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.rezilux.auth_services.Repository.UserRepository;
import com.rezilux.auth_services.Mappers.UserMapper;
import com.rezilux.auth_services.Models.*;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secreteKey;
    @PostConstruct
    protected void init(){
        secreteKey = Base64.getEncoder().encodeToString(secreteKey.getBytes());
    }
    public String createToken (UserDto dto){
        Date now  = new Date();
        Date validity = new Date(now.getTime()+5200000); //validité de 2h
        return JWT.create().withIssuer(dto.getLogin())
                .withIssuedAt(now)
              //  .withExpiresAt(validity)
                .withClaim("prenom",dto.getFirstName())
                .withClaim("nom",dto.getLastName())
                .sign(Algorithm.HMAC256(secreteKey));

    }

    public Authentication validateToken(String token){
        Algorithm algorithm =   Algorithm.HMAC256(secreteKey);
        JWTVerifier verifier  = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);
        UserDto u  =  UserDto.builder().login(decoded.getIssuer())
                .firstName(decoded.getClaim("prenom").asString())
                .lastName(decoded.getClaim("nom").asString()).build();
        return new UsernamePasswordAuthenticationToken(u,null , Collections.emptyList());

    }



    public Authentication strongerValidateToken (String token){
        Algorithm algorithm =   Algorithm.HMAC256(secreteKey);
        JWTVerifier verifier  = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);
      User user =   userRepository.findByLogin(decoded.getIssuer()).orElseThrow(()->  new AppException("Utilisateur introuvable", HttpStatus.NOT_FOUND) );

        return new UsernamePasswordAuthenticationToken(userMapper.toUserDto(user),null , Collections.emptyList());

    }




}
