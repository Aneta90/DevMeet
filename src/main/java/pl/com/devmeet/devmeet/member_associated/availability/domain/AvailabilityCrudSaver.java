package pl.com.devmeet.devmeet.member_associated.availability.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;


class AvailabilityCrudSaver implements CrudEntitySaver<AvailabilityEntity, AvailabilityEntity> {

    private AvailabilityCrudRepository repository;

    public AvailabilityCrudSaver(AvailabilityCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public AvailabilityEntity saveEntity(AvailabilityEntity entity) {
        return repository.save(entity);
    }
}

