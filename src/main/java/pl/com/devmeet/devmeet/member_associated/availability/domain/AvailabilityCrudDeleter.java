package pl.com.devmeet.devmeet.member_associated.availability.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityCrudDeleter implements CrudEntityDeleter<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudSaver availabilityCrudSaver;
    private AvailabilityCrudFinder availabilityCrudFinder;

//    public AvailabilityCrudDeleter(AvailabilityCrudRepository repository) {
//        this.availabilityCrudSaver = new AvailabilityCrudSaver(repository);
//        this.availabilityCrudFinder = new AvailabilityCrudFinder(repository);
//    }

    @Override
    public AvailabilityEntity deleteEntity(AvailabilityDto dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        AvailabilityEntity availability = availabilityCrudFinder.findEntity(dto);
        boolean availabilityActivity = availability.isActive();

        if (availabilityActivity) {
            availability.setActive(false);
            availability.setModificationTime(DateTime.now());

            return availabilityCrudSaver.saveEntity(availability);
        }

        throw new EntityAlreadyExistsException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
