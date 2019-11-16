package pl.com.devmeet.devmeet.member_associated.availability.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;


@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityCrudCreator implements CrudEntityCreator<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudSaver availabilityCrudSaver;
    private AvailabilityCrudFinder availabilityCrudFinder;
    private MemberCrudFacade memberCrudFacade;

    @Override
    public AvailabilityEntity createEntity(AvailabilityDto dto) throws AvailabilityAlreadyExistsException, MemberNotFoundException, UserNotFoundException {
        AvailabilityEntity availability;
//        boolean availabilityActivity;

        try {
            availability = availabilityCrudFinder.findEntity(dto);
            if (!availability.isActive() && availability.getModificationTime() != null)
                return availabilityCrudSaver.saveEntity(setDefaultValuesWhenAvailabilityExists(availability));
        } catch (AvailabilityNotFoundException e) {
            availability= setDefaultValuesWhenAvailabilityNotExists(AvailabilityCrudFacade.map(dto));
            return availabilityCrudSaver.saveEntity(availability);
        }

        throw new AvailabilityAlreadyExistsException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_ALREADY_EXISTS.toString());
    }

    private AvailabilityEntity setDefaultValuesWhenAvailabilityNotExists(AvailabilityEntity availability) {
        availability.setCreationTime(DateTime.now());
        availability.setActive(true);
        return availability;
    }

    private AvailabilityEntity setDefaultValuesWhenAvailabilityExists(AvailabilityEntity availability) {
        availability.setModificationTime(DateTime.now());
        availability.setActive(true);
        return availability;

    }
}

