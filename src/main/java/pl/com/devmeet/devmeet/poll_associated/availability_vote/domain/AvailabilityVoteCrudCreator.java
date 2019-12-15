package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteAlreadyExistsException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteCrudStatusEnum;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@Builder
@AllArgsConstructor
@NoArgsConstructor
class AvailabilityVoteCrudCreator implements CrudEntityCreator<AvailabilityVoteDto, AvailabilityVoteEntity> {

   private AvailabilityVoteCrudFinder voteCrudFinder;
   private AvailabilityVoteCrudSaver voteCrudSaver;

    @Override
    public AvailabilityVoteEntity createEntity(AvailabilityVoteDto dto) throws AvailabilityVoteAlreadyExistsException, AvailabilityNotFoundException, MemberNotFoundException, GroupNotFoundException, UserNotFoundException, PollNotFoundException {
        AvailabilityVoteEntity voteEntity;

        try {
            voteEntity = voteCrudFinder.findEntity(dto);
        }catch (AvailabilityVoteNotFoundException e){
            voteEntity = setDefaultValuesWhenVoteNotExist(AvailabilityVoteCrudFacade.map(dto));
            return voteCrudSaver.saveEntity(voteEntity);
        }

        throw new AvailabilityVoteAlreadyExistsException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_ALREADY_EXISTS.toString());
    }

    private AvailabilityVoteEntity setDefaultValuesWhenVoteNotExist(AvailabilityVoteEntity entity){
        entity.setCreationTime(DateTime.now());
        entity.setActive(true);

        return entity;
    }
}
