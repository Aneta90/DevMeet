package pl.com.devmeet.devmeet.member_associated.availability.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AvailabilityDto {

    private MemberDto member;
//    private PoolDto pool;
    private DateTime beginTime;
    private DateTime endTime;
    private PlaceDto place;
    private boolean remoteWork;
    private DateTime creationTime;
    private boolean isActive;

}
