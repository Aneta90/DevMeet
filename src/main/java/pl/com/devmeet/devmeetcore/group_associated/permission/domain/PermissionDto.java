package pl.com.devmeet.devmeetcore.group_associated.permission.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PermissionDto {

    private MemberDto member;
    private GroupDto group;
//    private PermissionTypeEnum type;

    private boolean possibleToVote;
    private boolean possibleToMessaging;
    private boolean possibleToChangeGroupName;
    private boolean possibleToBanMember;

    private boolean memberBaned;

    private DateTime creationTime;
    private DateTime modificationTime;
    private boolean isActive;
}
