package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

import java.util.List;

class AvailabilityVoteCrudFinder implements CrudEntityFinder<AvailabilityVoteDto, AvailabilityVoteEntity> {
    @Override
    public AvailabilityVoteEntity findEntity(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }

    @Override
    public List<AvailabilityVoteEntity> findEntities(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }

    @Override
    public boolean isExist(AvailabilityVoteDto dto) {
        return false;
    }
}
