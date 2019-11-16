package pl.com.devmeet.devmeet.member_associated.availability.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityCrudUpdater implements CrudEntityUpdater<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudSaver availabilityCrudSaver;
    private AvailabilityCrudFinder availabilityCrudFinder;

    @Override
    public AvailabilityEntity updateEntity(AvailabilityDto oldDto, AvailabilityDto newDto) throws MemberNotFoundException, UserNotFoundException, AvailabilityException, AvailabilityNotFoundException {
   //     AvailabilityEntity oldAvailability = checkIsOldAvailabilityActive(availabilityCrudFinder.findEntity(oldDto));
        AvailabilityEntity oldAvailability = findAvailabilityEntity(oldDto);
   //     AvailabilityEntity newAvailability = mapDtoToEntity(checkIfNewAvailabilityHasAMember(newDto, oldAvailability));
        AvailabilityEntity newAvailability = mapDtoToEntity(checkMember(oldDto, newDto));
        return availabilityCrudSaver.saveEntity(updateAllowedParameters(oldAvailability, newAvailability));
    }

    AvailabilityEntity findAvailabilityEntity(AvailabilityDto oldDto) throws MemberNotFoundException, AvailabilityNotFoundException, UserNotFoundException {
        return availabilityCrudFinder.findEntity(oldDto);
    }

    private AvailabilityDto checkMember(AvailabilityDto oldDto, AvailabilityDto newDto) throws AvailabilityException {
        if (oldDto.getMember().getNick().equals(newDto.getMember().getNick()))
                return newDto;
        throw new AvailabilityException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_NOT_FOUND.toString());    }

    private AvailabilityEntity mapDtoToEntity(AvailabilityDto dto) {
        return AvailabilityCrudFacade.map(dto);
    }

    private AvailabilityEntity updateAllowedParameters(AvailabilityEntity oldEntity, AvailabilityEntity newEntity) {
        oldEntity.setBeginTime(newEntity.getBeginTime());
        oldEntity.setEndTime(newEntity.getEndTime());
        oldEntity.setRemoteWork(newEntity.isRemoteWork());
//        oldEntity.setModificationTime(DateTime.now());
        return oldEntity;
    }
}
