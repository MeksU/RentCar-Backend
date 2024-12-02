package pl.meksu.rentcar.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetPasswordMessage {
    private String email;
    private String resetCode;
}
