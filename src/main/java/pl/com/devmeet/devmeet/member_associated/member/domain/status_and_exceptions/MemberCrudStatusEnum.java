package pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 12.11.2019
 * Time: 21:37
 */
public enum MemberCrudStatusEnum {

    MEMBER_ALREADY_EXIST("Member already exist"),
    MEMBER_NOT_FOUND("Member not found"),
    MEMBER_FOUND_BUT_NOT_ACTIVE("Member was found but is not active"),
    METHOD_NOT_IMPLEMENTED("Method not implemented"),
    MEMBER_USER_NOT_FOUND("Incorrect values. Member or group doesn't match");

    private String status;

    MemberCrudStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
