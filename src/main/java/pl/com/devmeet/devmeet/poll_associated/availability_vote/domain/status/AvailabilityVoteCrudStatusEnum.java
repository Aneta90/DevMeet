package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status;

public enum AvailabilityVoteCrudStatusEnum {

    AVAILABILITY_VOTE_ALREADY_EXISTS("Availability vote already exists"),
    AVAILABILITY_VOTE_NOT_FOUND("Availability vote not found"),
    AVAILABILITY_VOTES_NOT_FOUND("Availability votes not found"),
    AVAILABILITY_VOTE_FOUND_BUT_NOT_ACTIVE("Availability vote found but not active"),
    AVAILABILITY_VOTE_POLL_NOT_FOUND("Poll not found"),
    AVAILABILITY_VOTE_MEMBER_NOT_FOUND("Member not found"),
    AVAILABILITY_VOTE_AVAILABILITY_NOT_FOUND("Availability not found"),
    AVAILABILITY_VOTE_INCORRECT_POLL_MEMBER_OR_AVAILABILITY("Incorrect Poll, Member or Availability");

    private String message;

    AvailabilityVoteCrudStatusEnum(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
