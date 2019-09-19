package pl.com.devmeet.devmeet.domain.availability;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain.member.MemberDto;
import pl.com.devmeet.devmeet.domain.place.PlaceDto;
import pl.com.devmeet.devmeet.domain.user.UserDto;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AvailabilityDto {

    private MemberDto member;
    private DateTime beginTime;
    private DateTime endTime;
    private PlaceDto place;
    private boolean isActive;

}
