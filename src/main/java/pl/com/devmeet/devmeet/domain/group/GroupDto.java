package pl.com.devmeet.devmeet.domain.group;

import lombok.*;
import pl.com.devmeet.devmeet.domain.group_meeting.GroupMeetingDto;
import pl.com.devmeet.devmeet.domain.user.UserDto;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroupDto {

    private List<UserDto> users;
    private List<UserDto> organizers;
    private List<GroupMeetingDto> meetings;

}
