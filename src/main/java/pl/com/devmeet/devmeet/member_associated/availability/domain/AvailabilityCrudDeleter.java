package pl.com.devmeet.devmeet.member_associated.availability.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status.AvailabilityCrudInfoStatusEnum;

class AvailabilityCrudDeleter implements CrudEntityDeleter<AvailabilityDto, AvailabilityEntity> {

   private AvailabilityCrudSaver availabilityCrudSaver;
   private  AvailabilityCrudFinder availabilityCrudFinder;

    public AvailabilityCrudDeleter(AvailabilityCrudRepository repository) {
        this.availabilityCrudSaver = new AvailabilityCrudSaver(repository);
        this.availabilityCrudFinder = new AvailabilityCrudFinder(repository);
    }


    public AvailabilityEntity deleteEntity(AvailabilityDto dto) {
        AvailabilityEntity availability = availabilityCrudFinder.findEntity(dto);

        boolean availabilityActivity = availability.isActive();

        if (availabilityActivity) {
            availability.setActive(false);
            availability.setModificationTime(DateTime.now());
            return availabilityCrudSaver.saveEntity(availability);
        }

        throw new IllegalArgumentException(AvailabilityCrudInfoStatusEnum.GROUP_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
