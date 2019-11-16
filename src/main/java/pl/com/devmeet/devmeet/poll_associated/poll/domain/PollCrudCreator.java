package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollAlreadyExistsException;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollCrudStatusEnum;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.status_and_exceptions.PollNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PollCrudCreator implements CrudEntityCreator<PollDto, PollEntity> {

    private PollCrudSaver pollCrudSaver;
    private PollCrudFinder pollCrudFinder;

    @Override
    public PollEntity createEntity(PollDto dto) throws PollAlreadyExistsException, GroupNotFoundException {
        PollEntity poll;

        try {
            poll = pollCrudFinder.findEntity(dto);

            if (!poll.isActive()) {
                return pollCrudSaver.saveEntity(setDefaultValuesWhenPollExist(PollCrudFacade.map(dto)));
            }

        } catch (PollNotFoundException e) {
            poll = setDefaultValuesWhenPollNotExist(PollCrudFacade.map(dto));
            return pollCrudSaver.saveEntity(poll);
        }
        throw new PollAlreadyExistsException(PollCrudStatusEnum.POLL_ALREADY_EXISTS.toString());
    }

    private PollEntity setDefaultValuesWhenPollNotExist(PollEntity entity) {
        entity.setActive(true);
        entity.setCreationTime(DateTime.now());
        return entity;
    }

    private PollEntity setDefaultValuesWhenPollExist(PollEntity entity) {
        return setDefaultValuesWhenPollNotExist(entity);
    }
}
