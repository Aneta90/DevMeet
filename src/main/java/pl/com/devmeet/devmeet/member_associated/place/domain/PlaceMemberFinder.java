package pl.com.devmeet.devmeet.member_associated.place.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions.PlaceCrudStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceMemberFinder {

    private MemberCrudFacade memberCrudFacade;
    public MemberEntity findMember(MemberDto dto) throws EntityNotFoundException {
        try {
            return memberCrudFacade.findEntity(dto);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(PlaceCrudStatusEnum.PLACE_MEMBER_NOT_FOUND.toString());
        }
    }

}
