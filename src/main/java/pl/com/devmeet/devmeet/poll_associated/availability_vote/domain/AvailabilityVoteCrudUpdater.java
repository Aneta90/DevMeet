package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityEntity;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityVoteCrudUpdater implements CrudEntityUpdater<AvailabilityVoteDto, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudFinder voteCrudFinder;
    private AvailabilityVoteCrudSaver voteCrudSaver;
    private AvailabilityVoteAvailabilityFinder availabilityFinder;

    @Override
    public AvailabilityVoteEntity updateEntity(AvailabilityVoteDto oldDto, AvailabilityVoteDto newDto) throws MemberNotFoundException, UserNotFoundException, AvailabilityVoteNotFoundException, AvailabilityVoteException, GroupNotFoundException, AvailabilityNotFoundException, PollNotFoundException {
        AvailabilityVoteEntity oldVote = voteCrudFinder.findEntity(oldDto);
        AvailabilityEntity newVote = availabilityFinder.findAvailability(newDto.getAvailability());

        oldVote.setAvailability(newVote);

        return voteCrudSaver.saveEntity(oldVote);
    }
}
