package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

class PollCrudCreator implements CrudEntityCreator<PollDto, PollEntity> {
    @Override
    public PollEntity createEntity(PollDto dto) throws IllegalArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
        return null;
    }
}
