package pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions;

public enum GroupCrudStatusEnum {

    GROUP_ALREADY_EXISTS("Group already exists"),
    GROUP_NOT_FOUND("Group not found"),
    GROUPS_NOT_FOUND("Groups not found"),
    GROUP_FOUND_BUT_NOT_ACTIVE("Group was found but is not active"),
    GROUP_INCORRECT_VALUES("Group has incorrect value / values"),
    ARGUMENTS_NOT_SPECIFIED("Group name, website or description is not specified"),
    GROUP_NAME_NOT_SPECIFIED("Group name, website or description is not specified"),;

    private String status;

    GroupCrudStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
