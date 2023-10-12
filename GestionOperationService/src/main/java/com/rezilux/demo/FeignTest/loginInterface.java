package com.rezilux.demo.FeignTest;

import com.rezilux.demo.dtos.CredentialsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("AUTH-SERVICE")
public interface loginInterface {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialsDto crendentialsDto);
}
