package pl.com.devmeet.devmeet.group_associated.group.domain.status;

public enum GroupCrudInfoStatusEnum {

    GROUP_ALREADY_EXISTS("The group already exists"),
    GROUP_NOT_FOUND("The group not found"),
    GROUPS_NOT_FOUND("Groups not found"),
    GROUP_FOUND_BUT_NOT_ACTIVE("The group was found but is not active"),
    GROUP_INCORRECT_VALUES("The group has incorrect value / values");

    private String status;

    GroupCrudInfoStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
