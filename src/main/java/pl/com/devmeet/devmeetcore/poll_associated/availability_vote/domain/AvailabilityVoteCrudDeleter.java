package pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteCrudStatusEnum;
import pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteFoundButNotActiveException;
import pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteNotFoundException;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.status_and_exceptions.PollNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityVoteCrudDeleter implements CrudEntityDeleter<AvailabilityVoteDto, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudFinder voteCrudFinder;
    private AvailabilityVoteCrudSaver voteCrudSaver;

    @Override
    public AvailabilityVoteEntity deleteEntity(AvailabilityVoteDto dto) throws AvailabilityVoteFoundButNotActiveException, AvailabilityNotFoundException, MemberNotFoundException, GroupNotFoundException, UserNotFoundException, PollNotFoundException, AvailabilityVoteNotFoundException {
        AvailabilityVoteEntity found = voteCrudFinder.findEntity(dto);
        boolean voteActivity = found.isActive();

        if(voteActivity){
            found.setActive(false);

            return voteCrudSaver.saveEntity(found);
        }

        throw new AvailabilityVoteFoundButNotActiveException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
