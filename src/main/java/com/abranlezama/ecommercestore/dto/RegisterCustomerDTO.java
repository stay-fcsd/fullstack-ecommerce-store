package com.abranlezama.ecommercestore.dto;

import com.abranlezama.ecommercestore.annotations.USPhone;
import com.abranlezama.ecommercestore.annotations.USPostalCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterCustomerDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @USPhone String phoneNumber,
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String state,
        @USPostalCode String postal_code,
        @Email String email,
        @Length(min = 8, max = 15) String password
) {
}
