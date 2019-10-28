package pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions;

public enum AvailabilityCrudInfoStatusEnum {
    AVAILABILITY_ALREADY_EXISTS("Availability already exists"),
    AVAILABILITY_NOT_FOUND("Availability not found"),
    AVAILABILITIES_NOT_FOUND("Groups not found"),
    AVAILABILITY_FOUND_BUT_NOT_ACTIVE("Availability was found but is not active"),
    AVAILABILITY_MEMBER_NOT_FOUND("Member not found"),
    AVAILABILITY_INCORRECT_VALUES("Availability has incorrect value / values"),
    METHOD_NOT_IMPLEMENTED("Method not implemented");


    private String status;

    AvailabilityCrudInfoStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
