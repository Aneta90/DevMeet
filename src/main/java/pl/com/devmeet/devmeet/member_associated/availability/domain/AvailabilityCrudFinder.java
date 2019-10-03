package pl.com.devmeet.devmeet.member_associated.availability.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;

class AvailabilityCrudFinder implements CrudEntityFinder<AvailabilityDto, AvailabilityEntity> {

    @Override
    public AvailabilityEntity findEntity(AvailabilityDto dto) {
        return null;
    }

    @Override
    public boolean isExist(AvailabilityDto dto) {
        return false;
    }
}
