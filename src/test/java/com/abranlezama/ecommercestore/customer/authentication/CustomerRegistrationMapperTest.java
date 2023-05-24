package com.abranlezama.ecommercestore.customer.authentication;

import com.abranlezama.ecommercestore.customer.Customer;
import com.abranlezama.ecommercestore.objectmother.RegisterCustomerDTOMother;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerRegistrationMapperTest {

    CustomerRegistrationMapper mapper = Mappers.getMapper(CustomerRegistrationMapper.class);

    @Test
    void shouldConvertRegisterCustomerDTOToCustomerEntity() {
        // Given
        RegisterCustomerDTO dto = RegisterCustomerDTOMother.complete().build();

        // When
        Customer customer = mapper.mapRegisterDtoToCustomer(dto);

        // Then
        assertThat(customer.getName()).isEqualTo(dto.name());
        assertThat(customer.getEmail()).isEqualTo(dto.email());
        assertThat(customer.getPassword()).isEqualTo(dto.password());
    }

}