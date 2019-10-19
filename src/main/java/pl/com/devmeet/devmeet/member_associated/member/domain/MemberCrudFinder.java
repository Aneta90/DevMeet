package pl.com.devmeet.devmeet.member_associated.member.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

import java.util.List;

public class MemberCrudFinder implements CrudEntityFinder<MemberDto, MemberEntity> {

    private MemberRepository memberRepository;

    MemberCrudFinder(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberEntity findEntity(MemberDto dto) throws EntityNotFoundException {

        MemberEntity memberEntity;
        if (dto.getNick() != null) {
            memberEntity = memberRepository.findByNick(dto.getNick());
            return memberEntity;
        } else {
            throw new EntityNotFoundException("Not found");
        }
    }

    @Override
    public List<MemberEntity> findEntities(MemberDto dto) throws IllegalArgumentException {
        return null;
    }

   /* Optional<List<MemberEntity>> memberListByGroup(GroupDto groupDto) {
        return memberRepository.findByGroup(groupDto.getGroupName());
    }

    Optional<List<MemberEntity>> memberListByPlace(PlaceDto placeDto) {
        return memberRepository.findByPlace(placeDto.getPlaceName());
    }*/

    @Override
    public boolean isExist(MemberDto dto) {
        return memberRepository.findByNick(dto.getNick()) != null;
    }
}
