package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;
import pl.com.devmeet.devmeet.poll_associated.place_vote.domain.PlaceVoteDto;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PollDto {

    private GroupDto group;

    private List<PlaceVoteDto> placeVotes;
    private List<AvailabilityDto> availabilityVotes;

    private DateTime creationTime;
    private boolean active;
}
