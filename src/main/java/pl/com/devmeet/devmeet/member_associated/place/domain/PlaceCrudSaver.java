package pl.com.devmeet.devmeet.member_associated.place.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;

class PlaceCrudSaver implements CrudEntitySaver<PlaceEntity, PlaceEntity> {
    //??? 2 razy entity w argumencie?
    private PlaceCrudRepository repository;

    public PlaceCrudSaver(PlaceCrudRepository repository){
        this.repository=repository;
    }

    @Override
    public PlaceEntity saveEntity(PlaceEntity entity) {
        return repository.save(entity);
    }
}