package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;

import java.util.List;
import java.util.stream.Collectors;

class PollCrudMapper {

    public static PollDto map (PollEntity entity){
        return new PollDto().builder()
                .group(GroupCrudFacade.map(entity.getGroup()))
                .modificationTime(entity.getModificationTime())
                .creationTime(entity.getCreationTime())
                .isActive(entity.isActive())
                .build();
    }

    public static PollEntity map (PollDto dto){
        return new PollEntity().builder()
                .group(GroupCrudFacade.map(dto.getGroup()))
                .modificationTime(dto.getModificationTime())
                .creationTime(dto.getCreationTime())
                .active(dto.isActive())
                .build();
    }

    public static List<PollDto> mapToDtos(List<PollEntity> entities){
        return entities.stream()
                .map(entity -> map(entity))
                .collect(Collectors.toList());
    }

    public static List<PollEntity> mapToEntities(List<PollDto> dtos){
        return dtos.stream()
                .map(dto -> map(dto))
                .collect(Collectors.toList());
    }
}
