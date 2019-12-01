package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceCrudFacade;

import java.util.List;
import java.util.stream.Collectors;

public class MeetingMapper {


    public static MeetingDto map(MeetingEntity meetingEntity) {

        return meetingEntity != null ? MeetingDto.builder()
                .beginTime(meetingEntity.getCreationTime())
                .creationTime(meetingEntity.getCreationTime())
                .endTime(meetingEntity.getEndTime())
                .group(GroupCrudFacade.map(meetingEntity.getGroup()))
                .isActive(meetingEntity.isActive())
                .meetingNumber(meetingEntity.getMeetingNumber())
                .place(PlaceCrudFacade.map(meetingEntity.getPlace()))
                .build() : null;

    }

    public static MeetingEntity map(MeetingDto meetingDto) {

        return meetingDto != null ? MeetingEntity.builder()
                .beginTime(meetingDto.getBeginTime())
                .creationTime(meetingDto.getCreationTime())
                .endTime(meetingDto.getEndTime())
                .group(GroupCrudFacade.map(meetingDto.getGroup()))
                .isActive(meetingDto.isActive())
                .place(PlaceCrudFacade.map(meetingDto.getPlace()))
                .meetingNumber(meetingDto.getMeetingNumber())
                .build() : null;
    }

    public static List<MeetingDto> mapToDtos(List<MeetingEntity> entitiesList) {
        return entitiesList.stream()
                .map(MeetingMapper::map)
                .collect(Collectors.toList());
    }

    public static List<MeetingEntity> mapToEntities(List<MeetingDto> dtosList) {
        return dtosList.stream()
                .map(MeetingMapper::map)
                .collect(Collectors.toList());
    }
}
