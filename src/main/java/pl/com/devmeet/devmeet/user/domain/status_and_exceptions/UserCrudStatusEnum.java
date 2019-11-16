package pl.com.devmeet.devmeet.user.domain.status_and_exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 11:51
 */
public enum UserCrudStatusEnum {
    USER_NOT_EXISTS("User not exists"),
    USER_FOUND_BUT_NOT_ACTIVE("User found but not active"),
    USER_ALREADY_EXISTS("User already exists");

    private String status;

    UserCrudStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
