package pl.com.devmeet.devmeet.member_associated.place.domain;

import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;

import java.util.List;
import java.util.stream.Collectors;

class PlaceCrudMapper {

    public static PlaceDto map(PlaceEntity entity) {
        return new PlaceDto().builder()
                .member(MemberCrudFacade.map(entity.getMember()))
                .placeName(entity.getPlaceName())
                .description(entity.getDescription())
                .website(entity.getWebsite())
          //      .availability(entity.getAvailability())
                 .placeVotes(entity.getPlaceVotes())
                 .creationTime(entity.getCreationTime())
                 .modificationTime(entity.getModificationTime())
                 .isActive(entity.isActive())
                 .build();
    }

    public static PlaceEntity map(PlaceDto dto) {
        return new PlaceEntity().builder()
                .member(MemberCrudFacade.map(dto.getMember()))
                .placeName(dto.getPlaceName())
                .description(dto.getDescription())
                .website(dto.getWebsite())
          //      .availability(dto.getAvailability())
                .placeVotes(dto.getPlaceVotes())
                .creationTime(dto.getCreationTime())
                .modificationTime(dto.getModificationTime())
                .isActive(dto.isActive())
                .build();
    }

    public static List<PlaceDto> mapDtoList(List<PlaceEntity> entities) {
        return entities.stream()
                .map(entity -> map(entity))
                .collect(Collectors.toList());
    }

    public static List<PlaceEntity> mapEntityList(List<PlaceDto> dtos) {
        return dtos.stream()
                .map(dto -> map(dto))
                .collect(Collectors.toList());
    }
}

