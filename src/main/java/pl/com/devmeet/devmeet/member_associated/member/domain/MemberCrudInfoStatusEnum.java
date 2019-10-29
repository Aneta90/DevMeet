package pl.com.devmeet.devmeet.member_associated.member.domain;

public enum MemberCrudInfoStatusEnum {

    MEMBER_NOT_FOUND("Member not found in the system"),
    MEMBER_ALREADY_EXISTS("Member already exists");

    String status;

    MemberCrudInfoStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}