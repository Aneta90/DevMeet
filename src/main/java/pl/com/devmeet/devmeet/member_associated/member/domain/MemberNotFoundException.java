package pl.com.devmeet.devmeet.member_associated.member.domain;

public class MemberNotFoundException extends Exception {

    String MemberNotFoundException() {
        return "Member is not in database";
    }
}
