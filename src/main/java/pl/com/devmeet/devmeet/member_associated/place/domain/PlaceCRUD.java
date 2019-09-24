package pl.com.devmeet.devmeet.member_associated.place.domain;

public interface PlaceCRUD {

    PlaceDto create(PlaceDto dto);
    PlaceDto read(PlaceDto dto);
    PlaceDto update(PlaceDto newDto, PlaceDto oldDto);
    PlaceDto delete (PlaceDto dto);

    static PlaceDto map (PlaceEntity entity){
        return null;
    }

    static PlaceEntity map (PlaceDto dto){
        return null;
    }
}
