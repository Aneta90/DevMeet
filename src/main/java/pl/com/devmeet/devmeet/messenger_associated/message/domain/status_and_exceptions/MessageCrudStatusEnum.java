package pl.com.devmeet.devmeet.messenger_associated.message.domain.status_and_exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 02.12.2019
 * Time: 17:33
 */
public enum MessageCrudStatusEnum {
    MESSAGE_NOT_FOUND_BY_MEMBER("Message not found by member"),
    MESSAGE_NOT_FOUND_BY_GROUP("Message not found by group"),
    MESSAGE_NOT_FOUND_BY_SENDER_AND_MESSAGE("Message not found by sender and message"),
    MESSAGE_ALREADY_EXISTS("Message already exists"),
    MESSAGE_FOUND_BUT_NOT_ACTIVE("Message found but not active");

    private String message;

    MessageCrudStatusEnum(String message) {
        this.message = message;
    }
}
