package pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 11:54
 */
public enum UserStatusEnum {
    USER_DEFAULT_LOGIN_TYPE_NOT_DEFINED("User default login type not defined");

    private String status;

    UserStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
