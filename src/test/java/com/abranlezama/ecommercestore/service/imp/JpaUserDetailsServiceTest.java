package com.abranlezama.ecommercestore.service.imp;

import com.abranlezama.ecommercestore.exception.AuthenticationException;
import com.abranlezama.ecommercestore.exception.ExceptionMessages;
import com.abranlezama.ecommercestore.model.User;
import com.abranlezama.ecommercestore.objectmother.UserMother;
import com.abranlezama.ecommercestore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JpaUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private JpaUserDetailsService cut;

    @Test
    void shouldLoadUserFromDatabase() {
        // Given
        String userEmail = "duke.last@gmail.com";
        User user = UserMother.complete().build();

        given(userRepository.findByEmail(userEmail)).willReturn(Optional.of(user));

        // When
        cut.loadUserByUsername(userEmail);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserWithEmailDoesNotExist() {
        // Given
        String userEmail = "duke.last@gmail.com";

        given(userRepository.findByEmail(userEmail)).willReturn(Optional.empty());

        // When
        assertThatThrownBy(() -> cut.loadUserByUsername(userEmail))
                .hasMessage(ExceptionMessages.AUTHENTICATION_FAILED)
                .isInstanceOf(AuthenticationException.class);
    }


}