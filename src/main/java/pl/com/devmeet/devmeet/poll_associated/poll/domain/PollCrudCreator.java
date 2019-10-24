package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status.PollCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PollCrudCreator implements CrudEntityCreator<PollDto, PollEntity> {

    private PollCrudSaver pollCrudSaver;
    private PollCrudFinder pollCrudFinder;

    @Override
    public PollEntity createEntity(PollDto dto) throws IllegalArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
        PollEntity poll;

        try {
            poll = pollCrudFinder.findEntity(dto);

            if (!poll.isActive() && poll.getModificationTime() != null) {
                return pollCrudSaver.saveEntity(setDefaultValuesWhenPollExist(PollCrudFacade.map(dto)));
            }

        } catch (EntityNotFoundException e) {
            poll = setDefaultValuesWhenPollNotExist(PollCrudFacade.map(dto));
            return pollCrudSaver.saveEntity(poll);
        }
        throw new EntityAlreadyExistsException(PollCrudStatusEnum.POLL_ALREADY_EXISTS.toString());
    }

    private PollEntity setDefaultValuesWhenPollNotExist(PollEntity entity) {
        entity.setActive(true);
        entity.setCreationTime(DateTime.now());
        return null;
    }

    private PollEntity setDefaultValuesWhenPollExist(PollEntity entity) {
        entity.setActive(true);
        entity.setModificationTime(DateTime.now());
        return null;
    }
}
