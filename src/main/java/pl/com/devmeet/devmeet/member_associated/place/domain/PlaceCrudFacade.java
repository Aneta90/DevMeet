package pl.com.devmeet.devmeet.member_associated.place.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudInterface;

import java.util.List;

@Service
public class PlaceCrudFacade implements CrudInterface<PlaceDto, PlaceEntity> {

    private PlaceCrudRepository repository;

    @Autowired
    public PlaceCrudFacade(PlaceCrudRepository repository){
        this.repository = repository;
    }

    private PlaceCrudCreator initCreator() {
        return new  PlaceCrudCreator(repository);
    }

    private PlaceCrudFinder initFinder() {
        return new PlaceCrudFinder(repository);
    }

    private PlaceCrudUpdater initUpdater() {
        return new PlaceCrudUpdater(repository);
    }

    private PlaceCrudDeleter initDeleter() {
        return new PlaceCrudDeleter(repository);
    }


    @Override
    public PlaceDto create(PlaceDto dto) {
        return map(initCreator().createEntity(dto));
    }


    //po co castowanie tutaj było
    @Override
    public PlaceDto read(PlaceDto dto) {
        return map((PlaceEntity) initFinder().findEntity(dto));
    }

    @Override
    public List<PlaceDto> readAll(PlaceDto dto) {
        return mapDtoList(initFinder().findEntities(dto));
    }

    @Override
    public PlaceDto update(PlaceDto oldDto, PlaceDto newDto) {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public PlaceDto delete(PlaceDto dto) {
        return map(initDeleter().deleteEntity(dto));
    }

    @Override
    public PlaceEntity findEntity(PlaceDto dto) {
        return null;
    }

    @Override
    public List<PlaceEntity> findEntities(PlaceDto dto) {
        return null;
    }

    public static PlaceDto map(PlaceEntity entity) {
        return PlaceCrudMapper.map(entity);
    }

    public static List<PlaceDto> mapDtoList(List<PlaceEntity> entities) {
        return PlaceCrudMapper.mapDtoList(entities);
    }

    public static PlaceEntity map(PlaceDto dto) {
        return PlaceCrudMapper.map(dto);

    }

    public static List<PlaceEntity> mapEntityList(List<PlaceDto> dtos) {
        return PlaceCrudMapper.mapEntityList(dtos);
    }
}