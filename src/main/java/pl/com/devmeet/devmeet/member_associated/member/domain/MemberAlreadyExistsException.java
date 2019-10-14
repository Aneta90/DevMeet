package pl.com.devmeet.devmeet.member_associated.member.domain;

public class MemberAlreadyExistsException extends Exception {

    String MemberAlreadyExists() {
        return "Member already exists";
    }
}