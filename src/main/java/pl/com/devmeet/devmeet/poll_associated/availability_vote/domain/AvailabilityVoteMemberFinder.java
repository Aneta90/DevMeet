package pl.com.devmeet.devmeet.poll_associated.availability_vote.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.poll_associated.availability_vote.domain.status.AvailabilityVoteCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class AvailabilityVoteMemberFinder {

    private MemberCrudFacade memberCrudFacade;

    public MemberEntity findMember(MemberDto dto) throws EntityNotFoundException {
        try {
            return memberCrudFacade.findEntity(dto);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(AvailabilityVoteCrudStatusEnum.AVAILABILITY_VOTE_MEMBER_NOT_FOUND.toString());
        }
    }
}
