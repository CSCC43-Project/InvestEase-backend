package com.investease.backend.Controller;

import com.investease.backend.dto.LoginRequest;
import com.investease.backend.Model.User;
import com.investease.backend.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/checkLogin")
    public ResponseEntity<?> checkLogin(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("{\"response\": \"Email or Password Missing.\"}");
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"response\": \"Email or Password invalid.\"}");
        }

        User user = optionalUser.get();

        if (password.equals(user.getPassword())) {
            return ResponseEntity.ok("{\"response\": \"Successful login.\", \"userid\": " + user.getUserid() + "}");
        } else {
            return ResponseEntity.badRequest().body("{\"response\": \"Email or Password invalid.\"}");
        }
    }
}
