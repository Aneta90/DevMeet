package pl.com.devmeet.devmeetcore.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityDto;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.AvailabilityEntity;
import pl.com.devmeet.devmeetcore.member_associated.availability.domain.status_and_exceptions.AvailabilityNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@NoArgsConstructor
@AllArgsConstructor
@Builder
class AvailabilityVoteAvailabilityFinder {

    private AvailabilityCrudFacade availabilityCrudFacade;

    public AvailabilityEntity findAvailability(AvailabilityDto dto) throws MemberNotFoundException, AvailabilityNotFoundException, UserNotFoundException {
            return availabilityCrudFacade.findEntity(dto);
    }
}
