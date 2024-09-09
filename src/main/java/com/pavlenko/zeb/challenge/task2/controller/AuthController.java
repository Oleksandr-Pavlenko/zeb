package com.pavlenko.zeb.challenge.task2.controller;

import com.pavlenko.zeb.challenge.task2.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/token")
    public ResponseEntity<String> generateToken() {
        String token = JwtUtil.generateToken("testUser");
        return ResponseEntity.ok("Bearer " + token);
    }
}
