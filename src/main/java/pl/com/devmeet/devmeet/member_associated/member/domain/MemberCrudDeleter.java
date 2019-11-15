package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status.MemberCrudStatusEnum;

@RequiredArgsConstructor
class MemberCrudDeleter {

    @NonNull
    private MemberCrudFinder memberCrudFinder;
    @NonNull
    private MemberCrudSaver memberCrudSaver;

    public MemberEntity delete(MemberDto memberDto) throws EntityNotFoundException {

        MemberEntity memberEntity = memberCrudFinder.findEntity(memberDto);
        if (memberEntity.isActive()) {

            memberEntity.setActive(false);
            memberEntity.setModificationTime(DateTime.now());

            return saveMemberEntity(memberEntity);
        }

            throw new MemberNotFoundException(MemberCrudStatusEnum.MEMBER_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private MemberEntity saveMemberEntity(MemberEntity entity) {
        return memberCrudSaver.saveEntity(entity);
    }
}