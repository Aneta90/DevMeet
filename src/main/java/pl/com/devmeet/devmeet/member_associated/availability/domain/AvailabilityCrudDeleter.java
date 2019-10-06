package pl.com.devmeet.devmeet.member_associated.availability.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;

class AvailabilityCrudDeleter implements CrudEntityDeleter<AvailabilityDto, AvailabilityEntity> {

   private AvailabilityCrudSaver availabilityCrudSaver;


   //czemu SAVER?

    public AvailabilityCrudDeleter(AvailabilityCrudRepository repository) {
        this.availabilityCrudSaver = new AvailabilityCrudSaver(repository);
    }

    public AvailabilityEntity deleteEntity(AvailabilityDto dto) {
        return null;
    }
}
