package pl.com.devmeet.devmeet.group_associated.group.domain.status;

public enum GroupCrudInfoStatusEnum {

    GROUP_ALREADY_EXISTS("Group already exists"),
    GROUP_NOT_FOUND("Group not found"),
    GROUPS_NOT_FOUND("Groups not found"),
    GROUP_FOUND_BUT_NOT_ACTIVE("Group was found but is not active"),
    GROUP_INCORRECT_VALUES("Group has incorrect value / values");

    private String status;

    GroupCrudInfoStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
