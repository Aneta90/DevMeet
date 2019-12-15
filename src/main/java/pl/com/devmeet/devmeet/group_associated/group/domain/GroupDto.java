package pl.com.devmeet.devmeet.group_associated.group.domain;

import lombok.*;
import org.joda.time.DateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroupDto {

    private Long id;
    private String groupName;
    private String website;
    private String description;

//    private MessengerDto messenger;

    private Integer memberCounter;
    private Integer membersLimit;
    private Integer meetingCounter;

//    private List<MemberDto> members;
//    private List<PermissionDto> permissions;
//    private List<PollDto> polls;
//    private List<MeetingDto> meetings;

    private DateTime creationTime;
    private DateTime modificationTime;
    private boolean isActive;

}
