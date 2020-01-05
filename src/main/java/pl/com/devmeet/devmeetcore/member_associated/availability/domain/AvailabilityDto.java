package pl.com.devmeet.devmeetcore.member_associated.availability.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain.AvailabilityVoteEntity;

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

//    private PlaceDto place;

    private AvailabilityVoteEntity availabilityVote;

    private DateTime creationTime;
    private DateTime modificationTime;
    private boolean isActive;
}
