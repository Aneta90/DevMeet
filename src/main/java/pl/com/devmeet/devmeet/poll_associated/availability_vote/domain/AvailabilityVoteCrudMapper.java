package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudFacade;

import java.util.List;
import java.util.stream.Collectors;

class AvailabilityVoteCrudMapper {

    public static AvailabilityVoteEntity map (AvailabilityVoteDto dto){
        return dto != null ? AvailabilityVoteEntity.builder()
                .poll(PollCrudFacade.map(dto.getPoll()))
                .availability(AvailabilityCrudFacade.map(dto.getAvailability()))
                .member(MemberCrudFacade.map(dto.getMember()))
                .creationTime(dto.getCreationTime())
                .isActive(dto.isActive())
                .build() : null;
    }

    public static AvailabilityVoteDto map (AvailabilityVoteEntity entity){
        return entity != null ? AvailabilityVoteDto.builder()
                .poll(PollCrudFacade.map(entity.getPoll()))
                .availability(AvailabilityCrudFacade.map(entity.getAvailability()))
                .member(MemberCrudFacade.map(entity.getMember()))
                .creationTime(entity.getCreationTime())
                .isActive(entity.isActive())
                .build() : null;
    }

    public static List<AvailabilityVoteEntity> mapToEntities(List<AvailabilityVoteDto> dtos){
        return dtos.stream()
                .map(dto -> map(dto))
                .collect(Collectors.toList());
    }

    public static List<AvailabilityVoteDto> mapToDtos(List<AvailabilityVoteEntity> entities){
        return entities.stream()
                .map(AvailabilityVoteCrudMapper::map)
                .collect(Collectors.toList());
    }
 }
