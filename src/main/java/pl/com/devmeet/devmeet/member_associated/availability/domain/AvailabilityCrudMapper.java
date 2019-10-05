package pl.com.devmeet.devmeet.member_associated.availability.domain;

import java.util.List;
import java.util.stream.Collectors;

class AvailabilityCrudMapper {

    public static AvailabilityDto map(AvailabilityEntity entity) {
        return new AvailabilityDto().builder()
                .member(entity.getMember())
                .beginTime(entity.getBeginTime())
                .endTime(entity.getEndTime()
                        .remoteWork(entity.getRemoteWork())
                        .place(entity.getPlace())
                        .poll(entity.getPoll())
                        .creationTime(entity.getCreationTime())
                        .modificationTime(entity.getModificationTime())
                        .isActive(entity.isActive())
                        .build());
    }


    public static AvailabilityEntity map (AvailabilityDto dto) {
        return new AvailabilityEntity().builder()
                        .member(dto.getMember())
                        .beginTime(dto.getBeginTime())
                        .endTime(dto.getEndTime()
                        .remoteWork(dto.getRemoteWork())
                        .place(dto.getPlace())
                        .poll(dto.getPoll())
                        .creationTime(dto.getCreationTime())
                        .modificationTime(dto.getModificationTime())
                        .isActive(dto.isActive())
                        .build());
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

