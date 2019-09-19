package pl.com.devmeet.devmeet.domain.group_meeting;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain.group.GroupDto;
import pl.com.devmeet.devmeet.domain.place.PlaceDto;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroupMeetingDto {

    private String meetingName;
    private String website;
    private DateTime beginTime;
    private DateTime endTime;
    private PlaceDto place;
    private String description;
    private GroupDto group;

    private DateTime modificationTime;
    private DateTime createdTime;

    private boolean isActive;

}
