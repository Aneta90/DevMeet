package pl.com.devmeet.devmeet.group_associated.permission.domain;

enum PermissionTypeEnum {

    ADMIN(0),
    MEMBER(1),
    SPECTATOR(2);

    private Integer type;

    PermissionTypeEnum(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
