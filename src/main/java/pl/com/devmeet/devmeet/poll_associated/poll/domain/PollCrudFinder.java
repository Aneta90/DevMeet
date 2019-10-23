package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

import java.util.List;

class PollCrudFinder implements CrudEntityFinder<PollDto, PollEntity> {
    @Override
    public PollEntity findEntity(PollDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }

    @Override
    public List<PollEntity> findEntities(PollDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }

    @Override
    public boolean isExist(PollDto dto) {
        return false;
    }
}
