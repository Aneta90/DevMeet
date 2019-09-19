package pl.com.devmeet.devmeet.domain.group;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain.group_meeting.GroupMeetingDto;
import pl.com.devmeet.devmeet.domain.member.MemberDto;
import pl.com.devmeet.devmeet.domain.user.UserDto;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroupDto {

    private String groupName;

    private List<MemberDto> members;
    private List<MemberDto> organizers;
    private List<GroupMeetingDto> meetings;

    private DateTime creationTime;
    private DateTime modificationTime;

    private boolean isActive;
}
