package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.poll_associated.poll.domain.PollCrudFacade;

class AvailabilityVoteCrudMapper {

    public static AvailabilityVoteEntity map (AvailabilityVoteDto dto){
        return AvailabilityVoteEntity.builder()
                .poll(PollCrudFacade.map(dto.getPoll()))
                .availability(AvailabilityCrudFacade.map(dto.getAvailability()))
                .member(MemberCrudFacade.map(dto.getMember()))
                .creationTime(dto.getCreationTime())
                .isActive(dto.isActive())
                .build();
    }

    public static AvailabilityVoteDto map (AvailabilityVoteEntity entity){
        return AvailabilityVoteDto.builder()
                .poll(PollCrudFacade.map(entity.getPoll()))
                .availability(AvailabilityCrudFacade.map(entity.getAvailability()))
                .member(MemberCrudFacade.map(entity.getMember()))
                .creationTime(entity.getCreationTime())
                .isActive(entity.isActive())
                .build();
    }
}
