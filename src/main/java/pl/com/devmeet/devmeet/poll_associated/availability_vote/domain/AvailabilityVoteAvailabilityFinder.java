package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeet.member_associated.availability.domain.AvailabilityEntity;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status.AvailabilityVoteCrudStatusEnum;

@NoArgsConstructor
@AllArgsConstructor
@Builder
class AvailabilityVoteAvailabilityFinder {

    private AvailabilityCrudFacade availabilityCrudFacade;

    public AvailabilityEntity findAvailability(AvailabilityDto dto) throws EntityNotFoundException {
        try {
            return availabilityCrudFacade.findEntity(dto);
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_AVAILABILITY_NOT_FOUND.toString());
        }
    }
}
