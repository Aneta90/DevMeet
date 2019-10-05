package pl.com.devmeet.devmeet.member_associated.availability.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;

import java.util.List;

@Service
public class AvailabilityCrudFacade implements CrudInterface<AvailabilityDto, AvailabilityEntity> {

    private AvailabilityCrudRepository repository;

    @Autowired
    public AvailabilityCrudFacade(AvailabilityCrudRepository repository){
        this.repository = repository;
    }

    private AvailabilityCrudCreator initCreator() {
        return new AvailabilityCrudCreator(repository);
    }

    private AvailabilityCrudFinder initFinder() {
        return new AvailabilityCrudFinder(repository);
    }

    private AvailabilityCrudUpdater initUpdater() {
        return new AvailabilityCrudUpdater(repository);
    }

    private AvailabilityCrudDeleter initDeleter() {
        return new AvailabilityCrudDeleter(repository);
    }


    @Override
    public AvailabilityDto create(AvailabilityDto dto) {
        return map(initCreator().createEntity(dto));
    }

    @Override
    public AvailabilityDto read(AvailabilityDto dto) {
        return map(initFinder().findEntities(dto));
    }

    @Override
    public List<AvailabilityDto> readAll(AvailabilityDto dto) {
        return mapDtoList(initFinder().findEntities(dto));
    }

    @Override
    public AvailabilityDto update(AvailabilityDto oldDto, AvailabilityDto newDto) {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public AvailabilityDto delete(AvailabilityDto dto) {
        return map(initDeleter().deleteEntity(dto));
    }

    @Override
    public AvailabilityEntity findEntity(AvailabilityDto dto) {
        return null;
    }

    @Override
    public List<AvailabilityEntity> findEntities(AvailabilityDto dto) {
        return null;
    }

    private AvailabilityDto map(AvailabilityEntity entity) {
        return AvailabilityCrudMapper.map(entity);
    }

    private List<AvailabilityDto> mapDtoList(List<AvailabilityEntity> entities) {
        return AvailabilityCrudMapper.mapDtoList(entities);
    }

    private AvailabilityDto map(AvailabilityDto dto) {
        return AvailabilityCrudMapper.map(dto);
    }

    private List<AvailabilityDto> mapEntityList(List<AvailabilityDto> dtos) {
        return AvailabilityCrudMapper.mapEntityList(dtos);
    }
}
