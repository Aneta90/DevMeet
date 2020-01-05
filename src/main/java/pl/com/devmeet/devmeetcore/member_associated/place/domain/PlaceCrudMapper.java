package pl.com.devmeet.devmeetcore.member_associated.place.domain;

import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;

import java.util.List;
import java.util.stream.Collectors;

class PlaceCrudMapper {

    public static PlaceDto map(PlaceEntity entity) {
        return entity != null ? new PlaceDto().builder()
                .member(MemberCrudFacade.map(entity.getMember()))
                .placeName(entity.getPlaceName())
                .description(entity.getDescription())
                .website(entity.getWebsite())
//                .placeVotes(entity.getPlaceVotes())
                .creationTime(entity.getCreationTime())
                .modificationTime(entity.getModificationTime())
                .isActive(entity.isActive())
                .build() : null;
    }

    public static PlaceEntity map(PlaceDto dto) {
        return dto != null ? new PlaceEntity().builder()
                .member(MemberCrudFacade.map(dto.getMember()))
                .placeName(dto.getPlaceName())
                .description(dto.getDescription())
                .website(dto.getWebsite())
//                .placeVotes(dto.getPlaceVotes())
                .creationTime(dto.getCreationTime())
                .modificationTime(dto.getModificationTime())
                .isActive(dto.isActive())
                .build() : null;
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

