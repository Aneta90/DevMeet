package pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 02.12.2019
 * Time: 17:33
 */
public enum MessageCrudStatusEnum {
    MESSAGES_NOT_FOUND_BY_MEMBER("Messages not found by member"),
    MESSAGES_NOT_FOUND_BY_GROUP("Messages not found by group"),
    MESSAGE_NOT_FOUND_BY_SENDER_AND_MESSAGE("Message not found by sender and message"),
    MESSAGE_ALREADY_EXISTS("Message already exists"),
    MESSAGE_FOUND_BUT_NOT_ACTIVE("Message found but not active"),
    MESSAGE_IS_EMPTY("Message is empty"),
    NOT_SPECIFIED_SENDER("Sender not specified"),
    NOT_SPECIFIED_RECEIVER("Receiver not specified"),;

    private String message;

    MessageCrudStatusEnum(String message) {
        this.message = message;
    }
}
