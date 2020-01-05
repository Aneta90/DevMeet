package pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: 29.11.2019
 * Time: 18:53
 */

public enum MessengerInfoStatusEnum {
    MESSENGER_NOT_FOUND_BY_MEMBER("Messenger not found by member"),
    MESSENGER_NOT_FOUND_BY_GROUP("Messenger not found by group"),
    NOT_SPECIFIED_MEMBER_OR_GROUP("Member or group not specified"),
    NOT_SPECIFIED_GROUP("Group not specified"),
    MESSENGERS_OF_MEMBERS_NOT_FOUND("Messengers of members are not found"),
    MESSENGER_ALREADY_EXISTS("Messenger already exists"),
    MESSENGER_FOUND_BUT_NOT_ACTIVE("Messenger was found but is not active"),
    METHOD_NOT_IMPLEMENTED("Method not implemented");

    private String message;

    MessengerInfoStatusEnum(String message) {
        this.message = message;
    }
}
