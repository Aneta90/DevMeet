package pl.com.devmeet.devmeet.member_associated.member;

import pl.com.devmeet.devmeet.domain_utils.CrudInterface;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

import java.util.List;

public class MemberFacade implements CrudInterface<MemberDto, MemberEntity> {
    @Override
    public MemberDto create(MemberDto dto) {
        return null;
    }

    @Override
    public MemberDto read(MemberDto dto) {
        return null;
    }

    @Override
    public List<MemberDto> readAll(MemberDto dto) {
        return null;
    }

    @Override
    public MemberDto update(MemberDto oldDto, MemberDto newDto) {
        return null;
    }

    @Override
    public MemberDto delete(MemberDto dto) {
        return null;
    }

    @Override
    public MemberEntity findEntity(MemberDto dto) {
        return null;
    }

    @Override
    public List<MemberEntity> findEntities(MemberDto dto) {
        return null;
    }

    private MemberEntity map(MemberDto dto) {
        return MemberMapper.map(dto);
    }

    private MemberDto map(MemberEntity entity) {
        return MemberMapper.map(entity);
    }
}
