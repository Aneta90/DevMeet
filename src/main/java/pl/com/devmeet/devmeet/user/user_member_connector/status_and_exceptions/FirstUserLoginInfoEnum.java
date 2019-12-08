package pl.com.devmeet.devmeet.user.user_member_connector.status_and_exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 05.12.2019
 * Time: 23:44
 */
public enum FirstUserLoginInfoEnum {

    NEED_TO_CREATE_NEW_MEMBER("Member nof found for this ser. You must create new member"),
    SOMETHING_WRONG_WITH_USER("Something wrong with user"),
    MEMBER_CAN_NOT_HAVE_AN_EMPTY_NICKNAME("New member can not have an empty nickname");

    private String message;

    private FirstUserLoginInfoEnum(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
