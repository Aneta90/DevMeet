package pl.com.devmeet.devmeet.group_associated.permission.domain.status;

public enum PermissionCrudInfoStatusEnum {

    PERMISSION_ALREADY_EXISTS("Permission already exists"),
    PERMISSION_NOT_FOUND("Permission not found"),
    PERMISSIONS_NOT_FOUND("Permissions not found"),
    PERMISSION_FOUND_BUT_NOT_ACTIVE("Permission was found but is not active"),
    PERMISSION_INCORRECT_VALUES("Permission has incorrect value / values");

    private String status;

    PermissionCrudInfoStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
