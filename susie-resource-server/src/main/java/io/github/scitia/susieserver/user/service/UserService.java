package io.github.scitia.susieserver.user.service;

import io.github.scitia.susieserver.user.dto.UserDTO;

import java.util.Set;

public interface UserService {

    UserDTO getCurrentLoggedUser();
    String getCurrentLoggedUserUUID();
    UserDTO getUserByEmail(String email);
    UserDTO getUserByUUID(String uuid);
    UserDTO getUserSafely(String uuid);
    boolean isProjectOwner(Integer projectID);
    boolean isAnyProjectOwner(String uuid);
    Set<String> getAllProjectUsersUUIDs(Integer projectID);
    void saveAccountDataDeletionProcedure();
}
