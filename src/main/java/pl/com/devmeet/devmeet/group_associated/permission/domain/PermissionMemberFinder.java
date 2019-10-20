package pl.com.devmeet.devmeet.group_associated.permission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class PermissionMemberFinder {

    private MemberCrudFacade memberCrudFacade;

    public MemberEntity findMember(MemberDto dto) throws EntityNotFoundException {
        try {
            return memberCrudFacade.findEntity(dto);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(PermissionCrudStatusEnum.PERMISSION_MEMBER_NOT_FOUND.toString());
        }
    }
}
