package pl.meksu.rentcar.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String name;
    private String surname;
    private String mail;
    private String password;
    private String address;
    private String phone;
    private String postalCode;
}
