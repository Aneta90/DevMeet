package pl.com.devmeet.devmeet.group_associated.group.domain.status;

public enum GroupCrudErrorStatusEnum {

    GROUP_ALREADY_EXISTS("The group already exists"),
    GROUP_NOT_EXIST("The group does not exist");

    private String status;

    GroupCrudErrorStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status.toString();
    }
}
