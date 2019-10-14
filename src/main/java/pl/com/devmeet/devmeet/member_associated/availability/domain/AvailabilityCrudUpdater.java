package pl.com.devmeet.devmeet.member_associated.availability.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status.AvailabilityCrudInfoStatusEnum;

public class AvailabilityCrudUpdater implements CrudEntityUpdater<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudSaver availabilityCrudSaver;
    private AvailabilityCrudFinder availabilityCrudFinder;


    public AvailabilityCrudUpdater(AvailabilityCrudRepository repository) {
        this.availabilityCrudSaver = new AvailabilityCrudSaver(repository);
        this.availabilityCrudFinder = new AvailabilityCrudFinder(repository);
    }

    @Override
    public AvailabilityEntity updateEntity(AvailabilityDto oldDto, AvailabilityDto newDto) throws IllegalArgumentException {
        AvailabilityEntity foundOldAvailability = checkIsOldAvailabilityActive(availabilityCrudFinder.findEntity(oldDto));
        AvailabilityEntity newAvailability = mapDtoToEntity(checkIfNewAvailabilityHasAMember(newDto, foundOldAvailability));

        return availabilityCrudSaver.saveEntity(updateAllowedParameters(foundOldAvailability, newAvailability));
    }

    private AvailabilityEntity checkIsOldAvailabilityActive(AvailabilityEntity oldAvailability){
        if (oldAvailability.isActive())
            return oldAvailability;
        else
            throw new IllegalArgumentException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private AvailabilityDto checkIfNewAvailabilityHasAMember(AvailabilityDto newAvailability, AvailabilityEntity oldAvailability) {
        if (newAvailability.getMember().equals(oldAvailability.getMember()))
            return newAvailability;

        throw new IllegalArgumentException(AvailabilityCrudInfoStatusEnum
                .AVAILABILITY_INCORRECT_VALUES.toString());
    }

    private AvailabilityEntity mapDtoToEntity(AvailabilityDto dto) {
        return AvailabilityCrudFacade.map(dto);
    }

    private AvailabilityEntity updateAllowedParameters(AvailabilityEntity oldEntity, AvailabilityEntity newEntity) {
        oldEntity.setBeginTime(newEntity.getBeginTime());
        oldEntity.setEndTime(newEntity.getEndTime());
        oldEntity.setRemoteWork(newEntity.isRemoteWork());
        oldEntity.setPlace(newEntity.getPlace());
        oldEntity.setModificationTime(DateTime.now());
        return oldEntity;
    }
}
