package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
class PollCrudSaver implements CrudEntitySaver<PollEntity, PollEntity> {

    private PollCrudRepository pollCrudRepository;
    private PollGroupFinder pollGroupFinder;

    @Override
    public PollEntity saveEntity(PollEntity entity) throws GroupNotFoundException {
        return pollCrudRepository.save(connectPollWithGroup(entity));
    }

    private PollEntity connectPollWithGroup(PollEntity pollEntity) throws GroupNotFoundException {
        GroupEntity groupEntity = pollEntity.getGroup();

        if (groupEntity.getId() == null)
            groupEntity = pollGroupFinder.findGroup(GroupCrudFacade.map(groupEntity));

        pollEntity.setGroup(groupEntity);
        return pollEntity;
    }
}
