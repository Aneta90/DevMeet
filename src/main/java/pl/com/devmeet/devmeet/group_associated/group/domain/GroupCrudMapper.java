package pl.com.devmeet.devmeet.group_associated.group.domain;

import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerCrudFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GroupCrudMapper {

    public static GroupDto map(GroupEntity entity) {
        return entity != null ? new GroupDto().builder()
                .groupName(entity.getGroupName())
                .website(entity.getWebsite())
                .description(entity.getDescription())
                .messenger(MessengerCrudFacade.map(entity.getMessenger()))
                .membersLimit(entity.getMembersLimit())
                .memberCounter(entity.getMemberCounter())
                .meetingCounter(entity.getMeetingCounter())
                .creationTime(entity.getCreationTime())
                .modificationTime(entity.getModificationTime())
                .isActive(entity.isActive())
                .build() : null;
    }

    public static GroupEntity map(GroupDto dto) {
        return dto != null ? new GroupEntity().builder()
                .groupName(dto.getGroupName())
                .website(dto.getWebsite())
                .messenger(MessengerCrudFacade.map(dto.getMessenger()))
                .membersLimit(dto.getMembersLimit())
                .memberCounter(dto.getMemberCounter())
                .meetingCounter(dto.getMeetingCounter())
                .creationTime(dto.getCreationTime())
                .modificationTime(dto.getModificationTime())
                .isActive(dto.isActive())
                .build() : null;
    }

    public static List<GroupDto> mapDtoList(List<GroupEntity> entities) {
        return entities.stream()
                .map(entity -> map(entity))
                .collect(Collectors.toList());
    }

    public static List<GroupEntity> mapEntityList(List<GroupDto> dtos) {
        return dtos.stream()
                .map(dto -> map(dto))
                .collect(Collectors.toList());
    }
}
