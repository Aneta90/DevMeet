package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PollDto {

    private MemberDto member;
    private GroupDto group;

    private AvailabilityDto availability;
    private PlaceDto place;

    private DateTime creationTime;
}
