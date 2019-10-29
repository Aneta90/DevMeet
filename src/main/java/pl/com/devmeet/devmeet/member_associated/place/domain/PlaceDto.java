package pl.com.devmeet.devmeet.member_associated.place.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.poll_associated.place_vote.domain.PlaceVoteEntity;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollDto;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PlaceDto {

    private MemberDto member;

    private String placeName;
    private String description;
    private String website;
    private String location;

    private AvailabilityDto availability;

    private List<PlaceVoteEntity> placeVotes;

    private DateTime creationTime;
    private DateTime modificationTime;
    private boolean isActive;
}
