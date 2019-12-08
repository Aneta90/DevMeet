package pl.com.devmeet.devmeet.user.user_member_connector;

import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserDto;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;
import pl.com.devmeet.devmeet.user.user_member_connector.status_and_exceptions.CreateNewMemberException;
import pl.com.devmeet.devmeet.user.user_member_connector.status_and_exceptions.FirstUserLoginInfoEnum;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 05.12.2019
 * Time: 23:09
 */

public class UserMemberConnector {

    private MemberFinder memberFinder;

    public UserMemberConnector(MemberCrudFacade memberCrudFacade) {
        this.memberFinder = new MemberFinder(memberCrudFacade);
    }

    public MemberDto getUsersMemberElseThrow(UserDto existingUser) throws CreateNewMemberException {
        MemberDto foundMember;
        try {
            foundMember = memberFinder.findMember(existingUser);
        } catch (MemberNotFoundException | UserNotFoundException e) {
            throw new CreateNewMemberException(FirstUserLoginInfoEnum.NEED_TO_CREATE_NEW_MEMBER.toString());
        }
        return foundMember;
    }

    public MemberDto createNewMemberForUser(UserDto userDto, String memberNickname) throws CreateNewMemberException {
        MemberDto createdMember = null;
        checkMemberNickname(memberNickname);
        try {
            createdMember = memberFinder.addNewMember(userDto, memberNickname);
        } catch (UserNotFoundException e) {
            throw new CreateNewMemberException(FirstUserLoginInfoEnum.SOMETHING_WRONG_WITH_USER.toString());
        } catch (MemberAlreadyExistsException e) {
            throw new CreateNewMemberException(FirstUserLoginInfoEnum.MEMBER_CAN_NOT_HAVE_AN_EMPTY_NICKNAME.toString());
        }
        return createdMember;
    }

    private void checkMemberNickname(String string) throws CreateNewMemberException {
        NicknameValidator validator = new NicknameValidator();

        if (!validator.validate(string))
            throw new CreateNewMemberException(FirstUserLoginInfoEnum.MEMBER_CAN_NOT_HAVE_AN_EMPTY_NICKNAME.toString());
    }
}
