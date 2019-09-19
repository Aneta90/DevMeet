package pl.com.devmeet.devmeet.domain.member;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain.group.GroupDto;
import pl.com.devmeet.devmeet.domain.user.UserDto;

import java.util.List;

public class MemberDto {

    private String userName;
    private UserDto user;

    private List<GroupDto> groups;

    private DateTime creationTime;
    private DateTime modificationTime;

    private boolean isActive;

}
