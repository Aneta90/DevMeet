package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MeetingDto {

    private GroupDto group;
    private String meetingName;

    private DateTime beginTime;
    private DateTime endTime;
    private PlaceDto place;

    private DateTime modificationTime;
    private DateTime createdTime;

    private boolean isActive;
}
