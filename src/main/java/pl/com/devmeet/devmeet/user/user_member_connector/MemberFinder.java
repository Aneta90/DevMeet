package pl.com.devmeet.devmeet.user.user_member_connector;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserDto;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 05.12.2019
 * Time: 23:22
 */
@RequiredArgsConstructor
class MemberFinder {

    @NonNull
    private MemberCrudFacade memberCrudFacade;

    public MemberDto addNewMember(UserDto userDto, String memberNickname) throws UserNotFoundException, MemberAlreadyExistsException {
        return memberCrudFacade
                .add(MemberDto.builder()
                        .user(userDto)
                        .nick(memberNickname)
                        .build());
    }

    public MemberDto findMember(UserDto userDto) throws MemberNotFoundException, UserNotFoundException {
        return memberCrudFacade
                .find(MemberDto.builder()
                        .user(userDto)
                        .build()
                );
    }
}
