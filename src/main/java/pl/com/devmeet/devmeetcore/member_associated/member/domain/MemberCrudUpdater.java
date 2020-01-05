package pl.com.devmeet.devmeetcore.member_associated.member.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberCrudStatusEnum;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberFoundButNotActiveException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@RequiredArgsConstructor
class MemberCrudUpdater {

    @NonNull
    private MemberCrudFinder memberCrudFinder;
    @NonNull
    private MemberCrudSaver memberCrudSaver;

    public MemberEntity update(MemberDto oldDto, MemberDto newDto) throws MemberFoundButNotActiveException, MemberNotFoundException, UserNotFoundException {
        MemberEntity foundMember = findMember(oldDto);

        if (foundMember.isActive())
            return memberCrudSaver.saveEntity(updateAllowedValues(foundMember, newDto));

        throw new MemberFoundButNotActiveException(MemberCrudStatusEnum.MEMBER_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private MemberEntity findMember(MemberDto dto) throws MemberNotFoundException, UserNotFoundException {
        return memberCrudFinder.findEntity(dto);
    }

    private MemberEntity updateAllowedValues(MemberEntity oldMember, MemberDto updatedMember) {
        String nick = updatedMember.getNick();

        if (nick != null) {
            oldMember.setNick(nick);
            oldMember.setModificationTime(DateTime.now());
        }

        return oldMember;
    }
}
