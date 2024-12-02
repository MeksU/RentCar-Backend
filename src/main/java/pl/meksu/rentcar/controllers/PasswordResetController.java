package pl.meksu.rentcar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.meksu.rentcar.services.PasswordResetService;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/request-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestParam("email") String email) {
        try {
            passwordResetService.requestPasswordReset(email);
            return ResponseEntity.ok("Password reset request successful. Please check your email.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/verify-reset-code")
    public ResponseEntity<String> verifyResetCode(@RequestParam("email") String email,
                                                  @RequestParam("resetCode") String resetCode) {
        boolean isValid = passwordResetService.verifyResetCode(email, resetCode);
        if (isValid) {
            return ResponseEntity.ok("Code verified, proceed to reset password");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nieprawidłowy lub nieważny kod resetujący.");
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email,
                                                @RequestParam("newPassword") String newPassword) {
        boolean success = passwordResetService.resetPassword(email, newPassword);
        if (success) {
            return ResponseEntity.ok("Password reset successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password reset failed");
        }
    }
}