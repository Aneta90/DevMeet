package pl.com.devmeet.devmeet.member_associated.availability.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollDto;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AvailabilityDto {

    private MemberDto member;

    private DateTime beginTime;
    private DateTime endTime;
    private boolean remoteWork;

    private PlaceDto place;

    private PollDto pool;

    private DateTime creationTime;
    private boolean isActive;
}
