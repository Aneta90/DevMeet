package pl.com.devmeet.devmeet.member_associated.member.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;

public class MemberCrudSaver implements CrudEntitySaver<MemberDto, MemberEntity> {

    private MemberRepository memberRepository;

    MemberCrudSaver(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDto saveEntity(MemberEntity entity) {
        return MemberCrudInterface.map(memberRepository.save(entity));
    }
}