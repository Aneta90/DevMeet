package pl.com.devmeet.devmeet.member_associated.place.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PlaceCrudUpdater implements CrudEntityUpdater<PlaceDto, PlaceEntity> {

    private PlaceCrudSaver placeCrudSaver;
    private PlaceCrudFinder placeCrudFinder;

    @Override
    public PlaceEntity updateEntity(PlaceDto oldDto, PlaceDto newDto) throws EntityNotFoundException {
        PlaceEntity oldPlace = findPlaceEntity(oldDto);
        PlaceEntity newPlace = mapDtoToEntity(checkMember(oldDto, newDto));
        return placeCrudSaver.saveEntity(updateAllowedParameters(oldPlace, newPlace));
    }

    PlaceEntity findPlaceEntity(PlaceDto oldDto) throws EntityNotFoundException {
        return placeCrudFinder.findEntity(oldDto);
    }

    private PlaceDto checkMember(PlaceDto oldDto, PlaceDto newDto) throws EntityNotFoundException {
        if (oldDto.getMember().getNick() == newDto.getMember().getNick())
            return newDto;
        throw new EntityNotFoundException(PlaceCrudStatusEnum.PLACE_NOT_FOUND.toString());    }

    private PlaceEntity mapDtoToEntity(PlaceDto dto) {
        return PlaceCrudFacade.map(dto);
    }

    private PlaceEntity updateAllowedParameters(PlaceEntity oldEntity, PlaceEntity newEntity) {
        oldEntity.setPlaceName(newEntity.getPlaceName());
        oldEntity.setDescription(newEntity.getDescription());
        oldEntity.setWebsite(newEntity.getWebsite());
        oldEntity.setLocation(newEntity.getLocation());
        oldEntity.setModificationTime(DateTime.now());
        return oldEntity;
    }
}
