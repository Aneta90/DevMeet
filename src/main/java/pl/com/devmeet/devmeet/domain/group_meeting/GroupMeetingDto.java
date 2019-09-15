package pl.com.devmeet.devmeet.domain.group_meeting;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain.place.PlaceDto;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroupMeetingDto {

    private DateTime beginTime;
    private DateTime endTime;
    private PlaceDto place;

}
