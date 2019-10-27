package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

class AvailabilityVoteCrudCreator implements CrudEntityCreator<AvailabilityVoteDto, AvailabilityVoteEntity> {
    @Override
    public AvailabilityVoteEntity createEntity(AvailabilityVoteDto dto) throws IllegalArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
        return null;
    }
}
