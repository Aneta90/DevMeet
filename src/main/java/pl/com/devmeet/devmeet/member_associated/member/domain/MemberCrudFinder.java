package pl.com.devmeet.devmeet.member_associated.member.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

class MemberCrudFinder implements CrudEntityFinder<MemberDto, MemberEntity> {

    private MemberRepository memberRepository;

    MemberCrudFinder(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberEntity findEntity(MemberDto dto) throws EntityNotFoundException {

        Optional<MemberEntity> member = findMember(dto);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new EntityNotFoundException("Member not found");
        }
    }

    private Optional<MemberEntity> findMember(MemberDto memberDto) {
        String memberNick = memberDto.getNick();
        if (checkMemberNick(memberNick)) {
            return Optional.ofNullable(memberRepository.findByNick(memberNick));
        }
        return Optional.empty();
    }

    private boolean checkMemberNick(String memberNick) {
        return memberNick != null && !memberNick.equals("");
    }

    public MemberDto read(MemberDto dto) throws EntityNotFoundException {
        return getDtoFromEntity(findEntity(dto));
    }

    private MemberDto getDtoFromEntity(MemberEntity entity) {
        return MemberCrudInterface.map(entity);
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
