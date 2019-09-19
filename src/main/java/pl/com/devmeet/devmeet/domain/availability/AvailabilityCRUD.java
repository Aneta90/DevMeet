package pl.com.devmeet.devmeet.domain.availability;

import java.util.List;

public interface AvailabilityCRUD {

    AvailabilityDto create(AvailabilityDto dto);

    AvailabilityDto read(AvailabilityDto dto);

    AvailabilityDto update(AvailabilityDto newDto, AvailabilityDto oldDto);

    AvailabilityDto delete(AvailabilityDto dto);

    AvailabilityEntity findEntity(AvailabilityDto dto);

    List<AvailabilityEntity> findEntities();

}
