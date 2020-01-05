package pl.com.devmeet.devmeetcore.poll_associated.poll.domain;

import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudFacade;

import java.util.List;
import java.util.stream.Collectors;

class PollCrudMapper {

    public static PollDto map (PollEntity entity){
        return entity != null ? new PollDto().builder()
                .group(GroupCrudFacade.map(entity.getGroup()))
                .creationTime(entity.getCreationTime())
                .active(entity.isActive())
                .build() : null;
    }

    public static PollEntity map (PollDto dto){
        return dto != null ? new PollEntity().builder()
                .group(GroupCrudFacade.map(dto.getGroup()))
                .creationTime(dto.getCreationTime())
                .active(dto.isActive())
                .build() : null;
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
