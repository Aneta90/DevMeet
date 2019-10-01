package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.joda.time.DateTime;

import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.user.domain.UserDto;

import java.util.List;

public class MemberDto {

    private String userName;
    private UserDto user;

    private List<GroupDto> groups;

    private DateTime creationTime;
    private DateTime modificationTime;

    private boolean isActive;

}
