// Lab 1 // Factory Method

package io.github.scitia.susieserver.service;


import io.github.scitia.security.shared.user.SignUpResponse;
import io.github.scitia.security.shared.user.SignUpResult;
import io.github.scitia.security.shared.user.UserDetails;
import io.github.scitia.susieserver.security.config.KeycloakConnector;
import io.github.scitia.susieserver.user.factory.KeycloakUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test jednostkowy dla klasy KeycloakUserFactory implementującej wzorzec Factory Method.
 * Testujemy funkcjonalność tworzenia różnych typów użytkowników.
 */
class KeycloakUserFactoryTest {


    @Mock
    private KeycloakConnector keycloakConnector;

    private KeycloakUserFactory userFactory;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userFactory = new KeycloakUserFactory(keycloakConnector);
    }

    /**
     * Test sprawdzający, czy metoda createUser poprawnie deleguje tworzenie użytkownika
     * do klasy KeycloakConnector i zwraca oczekiwaną odpowiedź
     */
    @Test
    void createUser_shouldDelegateToKeycloakConnector() {
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername("test@example.com");
        userDetails.setPassword("password");
        userDetails.setFirstName("Test");
        userDetails.setLastName("User");

        SignUpResult result = SignUpResult.builder()
                .success(true)
                .result("Użytkownik został utworzony")
                .build();
        SignUpResponse expectedResponse = new SignUpResponse(201, result);


        when(keycloakConnector.signUp(any(UserDetails.class))).thenReturn(expectedResponse);
        SignUpResponse response = userFactory.createUser(userDetails);

        assertEquals(expectedResponse, response, "Odpowiedź powinna być zgodna z oczekiwaną");
        verify(keycloakConnector).signUp(userDetails);
    }


    @Test
    void createDevUser_shouldSetExtendedPrivilegesToFalse() {

        UserDetails userDetails = new UserDetails();
        userDetails.setUsername("dev@example.com");


        SignUpResult result = SignUpResult.builder().success(true).build();
        SignUpResponse expectedResponse = new SignUpResponse(201, result);


        when(keycloakConnector.signUp(any(UserDetails.class))).thenReturn(expectedResponse);
        userFactory.createDevUser(userDetails);

        assertFalse(userDetails.getExtendedPrivileges(), "Dla użytkownika Developer flaga extendedPrivileges powinna być false");
        verify(keycloakConnector).signUp(userDetails);
    }

    /**
     * Test sprawdzający, czy metoda createScrumMasterUser poprawnie ustawia flagę extendedPrivileges na true
     * przed przekazaniem danych do KeycloakConnector
     */
    @Test
    void createScrumMasterUser_shouldSetExtendedPrivilegesToTrue() {
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername("sm@example.com");

        SignUpResult result = SignUpResult.builder().success(true).build();
        SignUpResponse expectedResponse = new SignUpResponse(201, result);

        when(keycloakConnector.signUp(any(UserDetails.class))).thenReturn(expectedResponse);
        userFactory.createScrumMasterUser(userDetails);

        assertTrue(userDetails.getExtendedPrivileges(), "Dla użytkownika Scrum Master flaga extendedPrivileges powinna być true");
        verify(keycloakConnector).signUp(userDetails);
    }
}