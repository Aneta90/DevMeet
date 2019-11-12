package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

@RequiredArgsConstructor
class MemberCrudDeleter {

    @NonNull
    private MemberCrudFinder memberCrudFinder;
    @NonNull
    private MemberCrudSaver memberCrudSaver;

    public MemberEntity delete(MemberDto memberDto) throws IllegalArgumentException, EntityNotFoundException {

        MemberEntity memberEntity = memberCrudFinder.findEntity(memberDto);
        if (memberEntity.isActive()) {

            memberEntity.setActive(false);
            memberEntity.setModificationTime(DateTime.now());

            return saveMemberEntity(memberEntity);
        }

            throw new MemberNotFoundException("Member is not found in database or is not active");
    }

    private MemberEntity saveMemberEntity(MemberEntity entity) {
        return memberCrudSaver.saveEntity(entity);
    }
}