package pl.com.devmeet.devmeet.member_associated.availability.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityCrudUpdater implements CrudEntityUpdater<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudSaver availabilityCrudSaver;
    private AvailabilityCrudFinder availabilityCrudFinder;

    @Override
    public AvailabilityEntity updateEntity(AvailabilityDto oldDto, AvailabilityDto newDto) throws EntityNotFoundException {
   //     AvailabilityEntity oldAvailability = checkIsOldAvailabilityActive(availabilityCrudFinder.findEntity(oldDto));
        AvailabilityEntity oldAvailability = findAvailabilityEntity(oldDto);
   //     AvailabilityEntity newAvailability = mapDtoToEntity(checkIfNewAvailabilityHasAMember(newDto, oldAvailability));
        AvailabilityEntity newAvailability = mapDtoToEntity(checkMember(oldDto, newDto));
        return availabilityCrudSaver.saveEntity(updateAllowedParameters(oldAvailability, newAvailability));
    }

    AvailabilityEntity findAvailabilityEntity(AvailabilityDto oldDto) throws EntityNotFoundException {
        return availabilityCrudFinder.findEntity(oldDto);
    }

    private AvailabilityDto checkMember(AvailabilityDto oldDto, AvailabilityDto newDto){
        return null;
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
