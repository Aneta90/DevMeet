package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 29.11.2019
 * Time: 23:07
 */

@RequiredArgsConstructor
class MessengerMemberFinder {

    @NonNull
    private MemberCrudFacade memberCrudFacade;

    public MemberEntity findMember(MemberDto memberDto) throws MemberNotFoundException, UserNotFoundException {
        return memberCrudFacade.findEntity(memberDto);
    }
}
