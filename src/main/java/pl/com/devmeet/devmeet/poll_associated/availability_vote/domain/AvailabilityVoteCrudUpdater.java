package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status.AvailabilityVoteCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityVoteCrudUpdater implements CrudEntityUpdater<AvailabilityVoteDto, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudFinder voteCrudFinder;
    private AvailabilityVoteCrudSaver voteCrudSaver;

    @Override
    public AvailabilityVoteEntity updateEntity(AvailabilityVoteDto oldDto, AvailabilityVoteDto newDto) throws IllegalArgumentException, EntityNotFoundException, EntityAlreadyExistsException, UnsupportedOperationException {
        AvailabilityVoteEntity oldVote = voteCrudFinder.findEntity(oldDto);
        AvailabilityVoteEntity newVote = AvailabilityVoteCrudFacade.map(checkPollAndMemberAndAvailability(oldDto, newDto));

        return voteCrudSaver.saveEntity(newVote);
    }

    private AvailabilityVoteDto checkPollAndMemberAndAvailability(AvailabilityVoteDto oldDto, AvailabilityVoteDto newDto) throws EntityNotFoundException {
        if (newDto.getPoll() != null)
            if (newDto.getMember().getNick() != null)
                if (newDto.getAvailability() != null)
                    return newDto;

        throw new EntityNotFoundException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_INCORRECT_POLL_MEMBER_OR_AVAILABILITY.toString());
    }

    private AvailabilityVoteEntity updateAllowedParameters(AvailabilityVoteEntity oldEntity, AvailabilityVoteEntity newEntity) {
        oldEntity.setAvailability(newEntity.getAvailability());
        return oldEntity;
    }
}
