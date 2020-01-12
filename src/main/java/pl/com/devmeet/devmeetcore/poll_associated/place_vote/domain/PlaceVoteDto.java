package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.PlaceDto;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollDto;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PlaceVoteDto {

    private PollDto poll;
    private PlaceDto place;
    private MemberDto member;
    private DateTime creationTime;
    private boolean isActive;
}
