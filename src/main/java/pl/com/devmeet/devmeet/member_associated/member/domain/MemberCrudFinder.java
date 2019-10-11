package pl.com.devmeet.devmeet.member_associated.member.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.member_associated.place.domain.PlaceDto;

import java.util.List;
import java.util.Optional;

public class MemberCrudFinder implements CrudEntityFinder<MemberDto, MemberEntity> {

    private MemberRepository memberRepository;

    MemberCrudFinder(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberEntity findEntity(MemberDto dto) throws IllegalArgumentException, MemberNotFoundException {

        Optional<MemberEntity> memberEntity = Optional.empty();
        if (dto.getNick() != null) {
            memberEntity = memberRepository.findByNick(dto.getNick());
        }

        if (memberEntity.isPresent()) {
            return memberEntity.get();
        }
        throw new MemberNotFoundException();
    }

    @Override
    public List<MemberEntity> findEntities(MemberDto dto) throws IllegalArgumentException {
        return null;
    }

    Optional<List<MemberEntity>> memberListByGroup(GroupDto groupDto) {
        return memberRepository.findByGroup(groupDto.getGroupName());
    }

    Optional<List<MemberEntity>> memberListByPlace(PlaceDto placeDto) {
        return memberRepository.findByPlace(placeDto.getPlaceName());
    }

    @Override
    public boolean isExist(MemberDto dto) throws MemberNotFoundException {
        return findEntity(dto) != null;
    }
}