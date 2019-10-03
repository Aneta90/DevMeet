package pl.com.devmeet.devmeet.group_associated.group.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.meeting.domain.MeetingDto;
import pl.com.devmeet.devmeet.group_associated.permission.domain.PermissionDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroupDto {

    private String groupName;
    private String website;
    private String description;

    private MessengerDto messenger;

    private Integer memberCounter;
    private Integer membersMax;
    private Integer meetingCounter;

    private List<MemberDto> members;
    private List<PermissionDto> permissions;
    private List<PollDto> polls;
    private List<MeetingDto> meetings;

    private DateTime creationTime;
    private DateTime modificationTime;
    private boolean isActive;
}
