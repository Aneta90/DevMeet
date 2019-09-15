package pl.com.devmeet.devmeet.domain.group;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain.group_meeting.GroupMeetingDto;
import pl.com.devmeet.devmeet.domain.user.UserDto;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroupDto {

    private String groupName;
    private DateTime creationTime;

    private List<UserDto> users;
    private List<UserDto> organizers;
    private List<GroupMeetingDto> meetings;

    private boolean isActive;
}
