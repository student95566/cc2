package io.github.scitia.susieserver.security.controller;

import io.github.scitia.security.shared.identifier.UUID;
import io.github.scitia.security.shared.token.AccessTokenExtendedResponse;
import io.github.scitia.security.shared.token.RefreshTokenResponse;
import io.github.scitia.security.shared.user.AccountDeletionResult;
import io.github.scitia.security.shared.user.SignUpResponse;
import io.github.scitia.security.shared.user.UserCredentials;
import io.github.scitia.security.shared.user.UserDetails;
import io.github.scitia.security.shared.role.ExtendPermissionRequest;
import io.github.scitia.susieserver.security.config.KeycloakConnector;
import io.github.scitia.susieserver.user.dto.UserDTO;
import io.github.scitia.susieserver.user.factory.UserFactory;
import io.github.scitia.susieserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static io.github.scitia.security.dictionary.KeycloakDictionary.SM_PERMISSION;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class KeycloakController {

    private final KeycloakConnector keycloakConnector;
    private final UserService userService;

    // Lab 1 // Factory Method
    private final UserFactory userFactory;

    /*
    @PostMapping("/register")
    public ResponseEntity<SignUpResponse> register(@RequestBody UserDetails userDetails) {
        SignUpResponse response = keycloakConnector.signUp(userDetails);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
     */

    // Lab 1 // Factory Method
    @PostMapping("/register")
    public ResponseEntity<SignUpResponse> register(@RequestBody UserDetails userDetails) {
        SignUpResponse response = userFactory.createUser(userDetails);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<AccessTokenExtendedResponse> signIn(@RequestBody UserCredentials credentials) {
        return ResponseEntity.ok(keycloakConnector.signIn(credentials));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestParam String refreshToken) throws URISyntaxException, ExecutionException, InterruptedException {
        return ResponseEntity.ok(keycloakConnector.refreshToken(refreshToken));
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserDTO> userInfo() {
        return ResponseEntity.ok(userService.getCurrentLoggedUser());
    }

    @PreAuthorize(SM_PERMISSION)
    @PatchMapping("/user/permission")
    public void grantNewPermissionToUser(@RequestBody ExtendPermissionRequest permission) {
        Set<String> projectUsersUUIDs = userService.getAllProjectUsersUUIDs(permission.getProjectID());
        keycloakConnector.grantPermission(permission, projectUsersUUIDs);
    }

    @PreAuthorize(SM_PERMISSION)
    @PatchMapping("/user/permission-revoke")
    public void revokePermissionFromUser(@RequestBody ExtendPermissionRequest revokedPermission) {
        keycloakConnector.revokePermission(revokedPermission);
    }

    @DeleteMapping("/account-deletion")
    public ResponseEntity<AccountDeletionResult> deleteAccount() {
        userService.saveAccountDataDeletionProcedure();
        return ResponseEntity.ok(keycloakConnector.deleteAccount(new UUID(userService.getCurrentLoggedUserUUID())));
    }
}
