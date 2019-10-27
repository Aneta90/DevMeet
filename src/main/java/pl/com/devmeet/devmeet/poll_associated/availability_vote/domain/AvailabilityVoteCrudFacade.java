package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

import java.util.List;

@Service
public class AvailabilityVoteCrudFacade implements CrudInterface<AvailabilityVoteDto, AvailabilityVoteEntity> {



    @Override
    public AvailabilityVoteDto create(AvailabilityVoteDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        return null;
    }

    @Override
    public AvailabilityVoteDto read(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }

    @Override
    public List<AvailabilityVoteDto> readAll(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException, UnsupportedOperationException {
        return null;
    }

    @Override
    public AvailabilityVoteDto update(AvailabilityVoteDto oldDto, AvailabilityVoteDto newDto) throws IllegalArgumentException, EntityNotFoundException, EntityAlreadyExistsException {
        return null;
    }

    @Override
    public AvailabilityVoteDto delete(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException, EntityAlreadyExistsException {
        return null;
    }

    @Override
    public AvailabilityVoteEntity findEntity(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }

    @Override
    public List<AvailabilityVoteEntity> findEntities(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException, UnsupportedOperationException {
        return null;
    }
}
