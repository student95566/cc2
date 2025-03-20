package io.github.scitia.susieserver.user.dto;

public class UserDTOBuilder {
    private String uuid;
    private String email;
    private String firstName;
    private String lastName;

    UserDTOBuilder() {
    }

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public UserDTOBuilder uuid(final String uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserDTOBuilder email(final String email) {
        this.email = email;
        return this;
    }

    public UserDTOBuilder firstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserDTOBuilder lastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserDTO build() {
        return new UserDTO(this.uuid, this.email, this.firstName, this.lastName);
    }

    public String toString() {
        return "UserDTOBuilder(uuid=" + this.uuid + ", email=" + this.email + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ")";
    }
}
