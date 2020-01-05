package pl.com.devmeet.devmeetcore.poll_associated.poll.domain.status_and_exceptions;

public enum PollCrudStatusEnum {

    POLL_ALREADY_EXISTS("Poll already exists"),
    POLL_NOT_FOUND("Poll not found"),
    POLLS_NOT_FOUND("Polls not found"),
    POLL_FOUND_BUT_NOT_ACTIVE("Poll found but not active"),
    POLL_GROUP_NOT_FOUND("Group not found"),
    INCORRECT_GROUP("Incorrect values. Group doesn't match");

    private String message;

    PollCrudStatusEnum(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
