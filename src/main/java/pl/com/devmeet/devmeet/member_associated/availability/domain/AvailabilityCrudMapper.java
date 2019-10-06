package pl.com.devmeet.devmeet.member_associated.availability.domain;

import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;

import java.util.List;
import java.util.stream.Collectors;

class AvailabilityCrudMapper {

    //2 ostatnie metody z UserCrudInterface- nie mamy jeszcze Membera


    public static AvailabilityDto map(AvailabilityEntity entity) {
        return new AvailabilityDto().builder()
               .member(MemberCrudFacade.map(entity.getMember()))
                .beginTime(entity.getBeginTime())
                .endTime(entity.getEndTime())
                        .remoteWork(entity.isRemoteWork())
//                        .place(entity.getPlace())
    //                    .poll(entity.getPoll())
                        .creationTime(entity.getCreationTime())
                        .modificationTime(entity.getModificationTime())
                        .isActive(entity.isActive())
                        .build();
    }


    public static AvailabilityEntity map (AvailabilityDto dto) {
        return new AvailabilityEntity().builder()
     //                   .member(dto.getMember())
                        .beginTime(dto.getBeginTime())
                        .endTime(dto.getEndTime())
                        .remoteWork(dto.isRemoteWork())
       //                 .place(dto.getPlace())
         //               .poll(dto.getPoll())
                        .creationTime(dto.getCreationTime())
                        .modificationTime(dto.getModificationTime())
                        .isActive(dto.isActive())
                        .build();
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

