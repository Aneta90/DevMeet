package pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions;

public enum PermissionCrudStatusEnum {

    PERMISSION_ALREADY_EXISTS("Permission already exists"),
    PERMISSION_NOT_FOUND("Permission not found"),
    PERMISSIONS_NOT_FOUND("Permissions not found"),
    PERMISSION_FOUND_BUT_NOT_ACTIVE("Permission was found but is not active"),
    PERMISSION_MEMBER_NOT_FOUND("Member not found"),
    PERMISSION_GROUP_NOT_FOUND("Group not found"),
    METHOD_NOT_IMPLEMENTED("Method not implemented"),
    INCORRECT_MEMBER_OR_GROUP("Incorrect values. Member or group doesn't match");

    private String status;

    PermissionCrudStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
