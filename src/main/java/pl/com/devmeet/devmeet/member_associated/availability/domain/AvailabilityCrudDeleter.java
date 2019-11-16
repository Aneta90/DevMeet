package pl.com.devmeet.devmeet.member_associated.availability.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityCrudDeleter implements CrudEntityDeleter<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudSaver availabilityCrudSaver;
    private AvailabilityCrudFinder availabilityCrudFinder;

    @Override
    public AvailabilityEntity deleteEntity(AvailabilityDto dto) throws AvailabilityAlreadyExistsException, MemberNotFoundException, UserNotFoundException, AvailabilityNotFoundException {
        AvailabilityEntity availability = availabilityCrudFinder.findEntity(dto);
        boolean availabilityActivity = availability.isActive();

        if (availabilityActivity) {
            availability.setActive(false);
            availability.setModificationTime(DateTime.now());

            return availabilityCrudSaver.saveEntity(availability);
        }

        throw new AvailabilityAlreadyExistsException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_FOUND_BUT_NOT_ACTIVE.toString());
    }
}
