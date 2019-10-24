package pl.com.devmeet.devmeet.poll_associated.poll.domain;

import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;

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
                .isActive(dto.isActive())
                .build();
    }
}
