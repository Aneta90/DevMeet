package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteCrudStatusEnum;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityVoteCrudUpdater implements CrudEntityUpdater<AvailabilityVoteDto, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudFinder voteCrudFinder;
    private AvailabilityVoteCrudSaver voteCrudSaver;

    @Override
    public AvailabilityVoteEntity updateEntity(AvailabilityVoteDto oldDto, AvailabilityVoteDto newDto) throws MemberNotFoundException, UserNotFoundException, AvailabilityVoteNotFoundException, EntityNotFoundException, AvailabilityVoteException {
        AvailabilityVoteEntity oldVote = voteCrudFinder.findEntity(oldDto);
        AvailabilityVoteEntity newVote = checkPollAndMemberAndAvailability(oldVote, newDto);

        return voteCrudSaver.saveEntity(newVote);
    }

    private AvailabilityVoteEntity checkPollAndMemberAndAvailability(AvailabilityVoteEntity oldEntity, AvailabilityVoteDto newDto) throws AvailabilityVoteException {
        if (newDto.getPoll() != null)
            if (newDto.getMember().getNick() != null)
                if (newDto.getAvailability() != null)
                    return updateAllowedParameters(oldEntity, newDto);

        throw new AvailabilityVoteException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_INCORRECT_POLL_MEMBER_OR_AVAILABILITY.toString());
    }

    private AvailabilityVoteEntity updateAllowedParameters(AvailabilityVoteEntity oldEntity, AvailabilityVoteDto newDto) {
        oldEntity.setAvailability(AvailabilityCrudFacade.map(newDto.getAvailability()));
        return oldEntity;
    }
}
