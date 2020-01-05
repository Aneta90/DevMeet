package pl.com.devmeet.devmeetcore.group_associated.group.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 08.12.2019
 * Time: 13:54
 */
@RequiredArgsConstructor
class GroupMemberFinder {

    @NonNull
    private MemberCrudFacade memberCrudFacade;

    public MemberEntity findMember(MemberDto memberDto) throws MemberNotFoundException, UserNotFoundException {
        return memberCrudFacade.findEntity(memberDto);
    }
}
