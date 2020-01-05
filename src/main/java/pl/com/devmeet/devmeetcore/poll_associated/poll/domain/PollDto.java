package pl.com.devmeet.devmeetcore.poll_associated.poll.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.PlaceVoteDto;

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
