package pl.com.devmeet.devmeet.member_associated.availability;

import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityEntity;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;

import java.util.List;

public class AvailabilityCrudFacade implements CrudInterface<AvailabilityDto, AvailabilityEntity> {



    @Override
    public AvailabilityDto create(AvailabilityDto dto) {
        return null;
    }

    @Override
    public AvailabilityDto read(AvailabilityDto dto) {
        return null;
    }

    @Override
    public List<AvailabilityDto> readAll(AvailabilityDto dto) {
        return null;
    }

    @Override
    public AvailabilityDto update(AvailabilityDto oldDto, AvailabilityDto newDto) {
        return null;
    }

    @Override
    public AvailabilityDto delete(AvailabilityDto dto) {
        return null;
    }

    @Override
    public AvailabilityEntity findEntity(AvailabilityDto dto) {
        return null;
    }

    @Override
    public List<AvailabilityEntity> findEntities(AvailabilityDto dto) {
        return null;
    }

    private AvailabilityEntity map (AvailabilityDto dto){
        return AvailabilityMapper.map(dto);
    }

    private AvailabilityDto map (AvailabilityEntity entity){
        return AvailabilityMapper.map(entity);
    }

}
