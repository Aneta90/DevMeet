package pl.com.devmeet.devmeet.member_associated.member.domain;

import java.util.UUID;


public class MemberEntity {

    private UUID Id;

    private User user;

    private String nick;

    private List<Group> groups;

    private List<Group> availabilities;

    private List<Place> places;

    private Messanger messanger;

    private DataTime creationTime;

    private DateTime modificationTime;

    private boolean isActive;

}
