package io.github.scitia.susieserver.user.service;

import io.github.scitia.security.shared.identifier.Email;
import io.github.scitia.security.shared.identifier.UUID;
import io.github.scitia.susieserver.project.domain.Project;
import io.github.scitia.susieserver.project.repository.ProjectRepository;
import io.github.scitia.susieserver.security.config.KeycloakConnector;
import io.github.scitia.susieserver.user.dto.UserDTO;
import io.github.scitia.susieserver.user.dto.UserDTOBuilder;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.github.scitia.security.dictionary.KeycloakDictionary.EMPTY_USER_EMAIL;
import static io.github.scitia.security.dictionary.KeycloakDictionary.EMPTY_USER_FIRSTNAME;
import static io.github.scitia.security.dictionary.KeycloakDictionary.EMPTY_USER_LASTNAME;
import static io.github.scitia.security.dictionary.KeycloakDictionary.EMPTY_USER_UUID;
import static io.github.scitia.security.dictionary.KeycloakDictionary.KEYCLOAK_FIRSTNAME_TOKEN_CLAIM;
import static io.github.scitia.security.dictionary.KeycloakDictionary.KEYCLOAK_LASTNAME_TOKEN_CLAIM;
import static io.github.scitia.security.dictionary.KeycloakDictionary.SM_ACCOUNT_DELETION_FAILED;
import static io.github.scitia.susieserver.exception.dictionary.ExceptionMessages.KEYCLOAK_USER_DOES_NOT_EXISTS;
import static io.github.scitia.susieserver.exception.dictionary.ExceptionMessages.PROJECT_DOES_NOT_EXISTS;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KeycloakConnector connector;
    private final ProjectRepository projectRepository;

    @Override
    public UserDTO getCurrentLoggedUser() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return UserDTOBuilder.builder()
                .uuid(jwt.getSubject())
                .email(email)
                .firstName(jwt.getClaimAsString(KEYCLOAK_FIRSTNAME_TOKEN_CLAIM))
                .lastName(jwt.getClaimAsString(KEYCLOAK_LASTNAME_TOKEN_CLAIM))
                .build();
    }

    @Override
    public String getCurrentLoggedUserUUID() {
        return getCurrentLoggedUser().getUuid();
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        UserRepresentation userRepresentation = Optional.ofNullable(connector.getUserRepresentation(new Email(email)))
                .orElseThrow(() -> new RuntimeException(KEYCLOAK_USER_DOES_NOT_EXISTS));
        return UserDTOBuilder.builder()
                .uuid(userRepresentation.getId())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .build();
    }

    @Override
    public UserDTO getUserByUUID(String uuid) {
        UserRepresentation userRepresentation = Optional.of(connector.getUserRepresentation(new UUID(uuid)))
                .orElseThrow(() -> new RuntimeException(KEYCLOAK_USER_DOES_NOT_EXISTS));
        return UserDTOBuilder.builder()
                .uuid(uuid)
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .build();
    }

    @Override
    public UserDTO getUserSafely(String uuid) {

        if (isNull(uuid)) {
            return null;
        }
        return isKeycloakUserExistByUUID(uuid) ? getUserByUUID(uuid) : mockEmptyUser();
    }

    @Override
    public boolean isProjectOwner(Integer projectID) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String currentLoggedUserUUID = jwt.getSubject();
        Project project = projectRepository.findById(projectID).orElseThrow(RuntimeException::new);
        String projectOwnerUUID = project.getProjectOwner();
        return projectOwnerUUID.equals(currentLoggedUserUUID);
    }

    @Override
    public boolean isAnyProjectOwner(String uuid) {
        return projectRepository.existsByProjectOwner(uuid);
    }

    @Override
    public Set<String> getAllProjectUsersUUIDs(Integer projectID) {

        Project project = projectRepository.findById(projectID)
                .orElseThrow(() -> new RuntimeException(PROJECT_DOES_NOT_EXISTS));

        return project.getUserIDs();
    }

    @Override
    public void saveAccountDataDeletionProcedure() {
        String currentLoggedInUserUUID = getCurrentLoggedUserUUID();
        verifyAccountDeletionAbility(currentLoggedInUserUUID);
        deleteUserFromAllProjects(currentLoggedInUserUUID);
    }

    private boolean isKeycloakUserExistByUUID(String uuid) {
        return connector.isUserExist(new UUID(uuid));
    }

    private void verifyAccountDeletionAbility(String userUUID) {
        if (isAnyProjectOwner(userUUID)) {
            throw new RuntimeException(SM_ACCOUNT_DELETION_FAILED);
        }
    }

    private UserDTO mockEmptyUser() {
        return UserDTOBuilder.builder()
                .uuid(EMPTY_USER_UUID)
                .email(EMPTY_USER_EMAIL)
                .firstName(EMPTY_USER_FIRSTNAME)
                .lastName(EMPTY_USER_LASTNAME)
                .build();
    }

    private void deleteUserFromAllProjects(String uuid) {
        List<Project> allUsersProjects = projectRepository.findAllByUserIDsContains(uuid);
        if (!allUsersProjects.isEmpty()) {
            allUsersProjects.forEach(project -> {
                Set<String> projectUsers = project.getUserIDs();
                projectUsers.remove(uuid);
                project.setUserIDs(projectUsers);
                projectRepository.save(project);
            });
        }
    }
}
