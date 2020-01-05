package pl.com.devmeet.devmeetcore.member_associated.member.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntitySaver;

@RequiredArgsConstructor
class MemberCrudSaver implements CrudEntitySaver<MemberEntity, MemberEntity> {

    @NonNull
    private MemberRepository memberRepository;

    @Override
    public MemberEntity saveEntity(MemberEntity entity) {
        return memberRepository.save(entity);
    }
}