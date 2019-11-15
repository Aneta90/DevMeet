package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status_and_exceptions.AvailabilityVoteCrudStatusEnum;

@Builder
@AllArgsConstructor
@NoArgsConstructor
class AvailabilityVoteCrudCreator implements CrudEntityCreator<AvailabilityVoteDto, AvailabilityVoteEntity> {

   private AvailabilityVoteCrudFinder voteCrudFinder;
   private AvailabilityVoteCrudSaver voteCrudSaver;

    @Override
    public AvailabilityVoteEntity createEntity(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
        AvailabilityVoteEntity voteEntity;

        try {
            voteEntity = voteCrudFinder.findEntity(dto);
        }catch (EntityNotFoundException e){
            voteEntity = setDefaultValuesWhenVoteNotExist(AvailabilityVoteCrudFacade.map(dto));
            return voteCrudSaver.saveEntity(voteEntity);
        }

        throw new EntityAlreadyExistsException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_ALREADY_EXISTS.toString());
    }

    private AvailabilityVoteEntity setDefaultValuesWhenVoteNotExist(AvailabilityVoteEntity entity){
        entity.setCreationTime(DateTime.now());
        entity.setActive(true);

        return entity;
    }
}
