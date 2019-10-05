package pl.com.devmeet.devmeet.member_associated.availability.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import java.util.List;

class AvailabilityCrudFinder implements CrudEntityFinder<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudSaver availabilityCrudSaver;

    public AvailabilityCrudFinder(AvailabilityCrudRepository repository) {
        this.availabilityCrudSaver= new AvailabilityCrudSaver(repository);
    }

    @Override
    public AvailabilityEntity findEntity(AvailabilityDto dto) {
        return null;
    }

    @Override
    public List<AvailabilityEntity> findEntities (AvailabilityDto dto) {
        return null;
    }

    @Override
    public boolean isExist(AvailabilityDto dto) {
        return false;
    }
}
