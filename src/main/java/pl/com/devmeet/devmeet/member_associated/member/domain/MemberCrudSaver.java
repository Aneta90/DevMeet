package pl.com.devmeet.devmeet.member_associated.member.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;

public class MemberCrudSaver implements CrudEntitySaver<MemberEntity, MemberEntity> {

    private MemberRepository memberRepository;

    MemberCrudSaver(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberEntity saveEntity(MemberEntity entity) {
        return memberRepository.save(entity);
    }
}