package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AvailabilityVoteDto {

    private PollDto poll;

    private AvailabilityDto availability;

    private MemberDto member;

    private DateTime creationTime;
    private boolean isActive;
}
