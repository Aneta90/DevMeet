package pl.com.devmeet.devmeet.group_associated.permission.domain.status;

public enum PermissionCrudInfoStatusEnum {

    GROUP_ALREADY_EXISTS("Permission already exists"),
    GROUP_NOT_FOUND("Permission not found"),
    GROUPS_NOT_FOUND("Permission not found"),
    GROUP_FOUND_BUT_NOT_ACTIVE("Permission was found but is not active"),
    GROUP_INCORRECT_VALUES("Permission has incorrect value / values");

    private String status;

    PermissionCrudInfoStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
