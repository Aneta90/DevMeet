package pl.com.devmeet.devmeet.group_associated.group.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GroupCrudMapper {

    public static GroupDto map(GroupEntity entity) {
        return new GroupDto().builder()
                .groupName(entity.getGroupName())
                .website(entity.getWebsite())
                .description(entity.getDescription())
//               .messenger(entity.getMessenger())
                .membersLimit(entity.getMembersLimit())
                .memberCounter(entity.getMemberCounter())
                .meetingCounter(entity.getMeetingCounter())
                .creationTime(entity.getCreationTime())
                .modificationTime(entity.getModificationTime())
                .isActive(entity.isActive())
                .build();
    }

    public static GroupEntity map(GroupDto dto) {
        return new GroupEntity().builder()
                .groupName(dto.getGroupName())
                .website(dto.getWebsite())
                .description(dto.getDescription())
//                .messenger(dto.getMessenger())
                .membersLimit(dto.getMembersLimit())
                .memberCounter(dto.getMemberCounter())
                .meetingCounter(dto.getMeetingCounter())
                .creationTime(dto.getCreationTime())
                .modificationTime(dto.getModificationTime())
                .isActive(dto.isActive())
                .build();
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
