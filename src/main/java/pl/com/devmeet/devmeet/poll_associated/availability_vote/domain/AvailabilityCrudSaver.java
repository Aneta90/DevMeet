package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

class AvailabilityCrudSaver implements CrudEntitySaver<AvailabilityVoteDto, AvailabilityVoteEntity> {
    @Override
    public AvailabilityVoteDto saveEntity(AvailabilityVoteEntity entity) throws EntityNotFoundException {
        return null;
    }
}
