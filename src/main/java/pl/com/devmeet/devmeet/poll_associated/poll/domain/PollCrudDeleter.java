package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status.PollCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder

class PollCrudDeleter implements CrudEntityDeleter<PollDto,PollEntity> {

    private PollCrudSaver pollCrudSaver;
    private PollCrudFinder pollCrudFinder;

    @Override
    public PollEntity deleteEntity(PollDto dto) throws IllegalArgumentException, EntityNotFoundException, EntityAlreadyExistsException {
       PollEntity foundPoll = pollCrudFinder.findEntity(dto);
       boolean pollActivity = foundPoll.isActive();

       if(pollActivity){
           foundPoll.setActive(false);
           return pollCrudSaver.saveEntity(foundPoll);
       }

       throw new EntityAlreadyExistsException(PollCrudStatusEnum.POLL_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
