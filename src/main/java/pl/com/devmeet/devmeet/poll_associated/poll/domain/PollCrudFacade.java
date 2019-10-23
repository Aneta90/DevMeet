package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;

import java.util.List;

public class PollCrudFacade implements CrudInterface<PollDto, PollEntity> {

    private GroupCrudRepository groupCrudRepository;
    private PollCrudRepository pollCrudRepository;

    @Override
    public PollDto create(PollDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        return null;
    }

    @Override
    public PollDto read(PollDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }

    @Override
    public List<PollDto> readAll(PollDto dto) throws IllegalArgumentException, EntityNotFoundException, UnsupportedOperationException {
        return null;
    }

    @Override
    public PollDto update(PollDto oldDto, PollDto newDto) throws IllegalArgumentException, EntityNotFoundException, EntityAlreadyExistsException {
        return null;
    }

    @Override
    public PollDto delete(PollDto dto) throws IllegalArgumentException, EntityNotFoundException, EntityAlreadyExistsException {
        return null;
    }

    @Override
    public PollEntity findEntity(PollDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }

    @Override
    public List<PollEntity> findEntities(PollDto dto) throws IllegalArgumentException, EntityNotFoundException, UnsupportedOperationException {
        return null;
    }
}
