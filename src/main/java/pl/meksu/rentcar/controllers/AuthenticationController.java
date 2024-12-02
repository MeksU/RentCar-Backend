package pl.meksu.rentcar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.meksu.rentcar.dto.UserRegistrationDTO;
import pl.meksu.rentcar.util.ResponseValue;
import pl.meksu.rentcar.models.User;
import pl.meksu.rentcar.services.UserService;
import pl.meksu.rentcar.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getMail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.getUserByMail(loginRequest.getMail()).orElseThrow(() -> new RuntimeException("Użytkownik o podanym e-mailu nie istnieje."));

            String jwt = jwtUtil.generateToken(user.getMail(),String.valueOf(user.getId()), user.getType());
            Map<String, String> response = new HashMap<>();
            response.put("jwt", jwt);
            response.put("userId", String.valueOf(user.getId()));
            response.put("userName", String.valueOf(user.getName()));
            response.put("userType", String.valueOf(user.getType()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseValue(e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO registrationDTO) {
        if (!userService.isMailUnique(registrationDTO.getMail())) {
            return ResponseEntity.badRequest().body(new ResponseValue("Użytkownik o podanym e-mailu istnieje."));
        }

        User registeredUser = userService.registerUser(
                registrationDTO.getName(),
                registrationDTO.getSurname(),
                registrationDTO.getMail(),
                registrationDTO.getPassword(),
                "user",
                registrationDTO.getAddress(),
                registrationDTO.getPhone(),
                registrationDTO.getPostalCode()
        );

        return ResponseEntity.ok(new ResponseValue("Użytkownik zarejestrowany pomyślnie: " + registeredUser.getName()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam("token") String token) {
        try {
            if (token == null || token.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseValue("Token nie może być pusty."));
            }

            // Wyciągnij nazwę użytkownika (email) z tokenu
            String username = jwtUtil.extractUsername(token);

            // Zweryfikuj token
            if (!jwtUtil.isTokenValid(token, username)) {
                return ResponseEntity.badRequest().body(new ResponseValue("Nieprawidłowy token JWT."));
            }

            // Pobierz użytkownika na podstawie emaila
            User user = userService.getUserByMail(username)
                    .orElseThrow(() -> new RuntimeException("Użytkownik o podanym e-mailu nie istnieje."));

            // Generuj nowy token
            String newJwt = jwtUtil.generateToken(user.getMail(), String.valueOf(user.getId()), user.getType());

            // Przygotuj odpowiedź
            Map<String, String> response = new HashMap<>();
            response.put("jwt", newJwt);
            response.put("userId", String.valueOf(user.getId()));
            response.put("userName", user.getName());
            response.put("userType", user.getType());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseValue(e.getMessage()));
        }
    }
}