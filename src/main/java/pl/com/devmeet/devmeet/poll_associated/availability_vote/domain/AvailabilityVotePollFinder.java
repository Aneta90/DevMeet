package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status.AvailabilityVoteCrudStatusEnum;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudFacade;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollDto;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollEntity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityVotePollFinder {

    private PollCrudFacade pollCrudFacade;

    public PollEntity findPoll(PollDto dto){
        try {
            return pollCrudFacade.findEntity(dto);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_POLL_NOT_FOUND.toString())
        }
    }

}
