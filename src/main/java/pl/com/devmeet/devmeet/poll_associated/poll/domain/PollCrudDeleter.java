package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollCrudStatusEnum;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder

class PollCrudDeleter implements CrudEntityDeleter<PollDto,PollEntity> {

    private PollCrudSaver pollCrudSaver;
    private PollCrudFinder pollCrudFinder;

    @Override
    public PollEntity deleteEntity(PollDto dto) throws PollNotFoundException, GroupNotFoundException {
       PollEntity foundPoll = pollCrudFinder.findEntity(dto);
       boolean pollActivity = foundPoll.isActive();

       if(pollActivity){
           foundPoll.setActive(false);
           return pollCrudSaver.saveEntity(foundPoll);
       }

       throw new PollNotFoundException(PollCrudStatusEnum.POLL_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
