package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status.AvailabilityVoteCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityVoteCrudDeleter implements CrudEntityDeleter<AvailabilityVoteDto, AvailabilityVoteEntity> {

    private AvailabilityVoteCrudFinder voteCrudFinder;
    private AvailabilityVoteCrudSaver voteCrudSaver;

    @Override
    public AvailabilityVoteEntity deleteEntity(AvailabilityVoteDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        AvailabilityVoteEntity found = voteCrudFinder.findEntity(dto);
        boolean voteActivity = found.isActive();

        if(voteActivity){
            found.setActive(false);

            return voteCrudSaver.saveEntity(found);
        }

        throw new EntityAlreadyExistsException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
