package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
class PollCrudSaver implements CrudEntitySaver<PollEntity, PollEntity> {

    private PollCrudRepository pollCrudRepository;
    private PollGroupFinder pollGroupFinder;

    @Override
    public PollEntity saveEntity(PollEntity entity) throws EntityNotFoundException {
        return pollCrudRepository.save(connectPollWithGroup(entity));
    }

    private PollEntity connectPollWithGroup(PollEntity pollEntity) throws EntityNotFoundException {
        GroupEntity groupEntity = pollEntity.getGroup();

        if (groupEntity.getId() == null)
            groupEntity = pollGroupFinder.findGroup(GroupCrudFacade.map(groupEntity));

        pollEntity.setGroup(groupEntity);
        return pollEntity;
    }
}
