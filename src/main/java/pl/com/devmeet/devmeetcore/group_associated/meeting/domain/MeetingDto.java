package pl.com.devmeet.devmeetcore.group_associated.meeting.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.PlaceDto;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MeetingDto {

    private GroupDto group;
    private Integer meetingNumber;

    private DateTime beginTime;
    private DateTime endTime;
    private PlaceDto place;

    private DateTime creationTime;
    private boolean isActive;
}
