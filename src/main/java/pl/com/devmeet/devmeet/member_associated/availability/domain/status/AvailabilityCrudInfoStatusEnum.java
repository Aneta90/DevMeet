package pl.com.devmeet.devmeet.member_associated.availability.domain.status;

public enum AvailabilityCrudInfoStatusEnum {

    AVAILABILITY_ALREADY_EXISTS("The availability already exists"),
    GROUP_NOT_FOUND("The availability not found"),
    GROUPS_NOT_FOUND("Availabilities not found"),
    GROUP_FOUND_BUT_NOT_ACTIVE("The availability was found but is not active"),
    GROUP_INCORRECT_VALUES("The availability has incorrect value / values");

    private String status;

    AvailabilityCrudInfoStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

}
