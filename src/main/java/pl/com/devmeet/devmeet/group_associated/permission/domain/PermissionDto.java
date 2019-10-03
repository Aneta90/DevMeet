package pl.com.devmeet.devmeet.group_associated.permission.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;

public class PermissionDto {

    private GroupDto group;
    private MemberDto member;
    private PermissionTypeEnum type;

    private boolean possibleToVote;
    private boolean possibleToMessaging;
    private boolean possibleToChangeGroupName;
    private boolean possibleToBanMember;

    private DateTime creationTime;
    private DateTime modificationTime;
    private boolean isActive;
}
