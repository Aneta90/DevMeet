package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberFoundButNotActiveException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@RequiredArgsConstructor
class MemberCrudDeleter {

    @NonNull
    private MemberCrudFinder memberCrudFinder;
    @NonNull
    private MemberCrudSaver memberCrudSaver;

    public MemberEntity delete(MemberDto memberDto) throws MemberFoundButNotActiveException, MemberNotFoundException, UserNotFoundException {
        MemberEntity memberEntity = findMember(memberDto);

        if (memberEntity.isActive())
            return saveMember(
                    setDefaultValuesWhenMemberIsDelete(memberEntity));

        throw new MemberFoundButNotActiveException(MemberCrudStatusEnum.MEMBER_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private MemberEntity setDefaultValuesWhenMemberIsDelete(MemberEntity memberEntity) {
        memberEntity.setActive(false);
        memberEntity.setModificationTime(DateTime.now());

        return memberEntity;
    }

    private MemberEntity findMember(MemberDto dto) throws MemberNotFoundException, UserNotFoundException {
        return memberCrudFinder.findEntity(dto);
    }

    private MemberEntity saveMember(MemberEntity entity) {
        return memberCrudSaver.saveEntity(entity);
    }
}