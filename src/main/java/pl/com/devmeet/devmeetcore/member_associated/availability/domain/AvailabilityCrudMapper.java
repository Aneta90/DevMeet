package pl.com.devmeet.devmeetcore.member_associated.availability.domain;

import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;

import java.util.List;
import java.util.stream.Collectors;

class AvailabilityCrudMapper {

    public static AvailabilityDto map(AvailabilityEntity entity) {
        return entity != null ? new AvailabilityDto().builder()
                .member(MemberCrudFacade.map(entity.getMember()))
                .beginTime(entity.getBeginTime())
                .endTime(entity.getEndTime())
                .remoteWork(entity.isRemoteWork())
                .availabilityVote(entity.getAvailabilityVote())
                .creationTime(entity.getCreationTime())
                .modificationTime(entity.getModificationTime())
                .isActive(entity.isActive())
                .build() : null;
    }

    public static AvailabilityEntity map(AvailabilityDto dto) {
        return dto != null ? new AvailabilityEntity().builder()
                .member(MemberCrudFacade.map(dto.getMember()))
                .beginTime(dto.getBeginTime())
                .endTime(dto.getEndTime())
                .remoteWork(dto.isRemoteWork())
                .availabilityVote(dto.getAvailabilityVote())
                .creationTime(dto.getCreationTime())
                .modificationTime(dto.getModificationTime())
                .isActive(dto.isActive())
                .build() : null;
    }

    public static List<AvailabilityDto> mapDtoList(List<AvailabilityEntity> entities) {
        return entities.stream()
                .map(entity -> map(entity))
                .collect(Collectors.toList());
    }

    public static List<AvailabilityEntity> mapEntityList(List<AvailabilityDto> dtos) {
        return dtos.stream()
                .map(dto -> map(dto))
                .collect(Collectors.toList());
    }
}

