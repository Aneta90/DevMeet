package pl.com.devmeet.devmeet.member_associated.availability.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.availability.domain.status_and_exceptions.AvailabilityCrudInfoStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailabilityMemberFinder {

    private MemberCrudFacade memberCrudFacade;
    public MemberEntity findMember(MemberDto dto) throws EntityNotFoundException {
        try {
            return memberCrudFacade.findEntity(dto);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(AvailabilityCrudInfoStatusEnum.AVAILABILITY_MEMBER_NOT_FOUND.toString());
        }
    }

}
