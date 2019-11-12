package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserCrudFacade;
import pl.com.devmeet.devmeet.user.domain.UserDto;
import pl.com.devmeet.devmeet.user.domain.UserEntity;

import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
class MemberCrudFinder implements CrudEntityFinder<MemberDto, MemberEntity> {

    @NonNull
    private MemberRepository memberRepository;
    @NonNull
    private MemberUserFinder userFinder;

    @Override
    public MemberEntity findEntity(MemberDto dto) throws EntityNotFoundException {

        Optional<MemberEntity> member = findMember(dto);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new EntityNotFoundException("Member not found");
        }
    }

    private UserEntity findUser(UserDto dto) throws EntityNotFoundException {
        return userFinder.findUser(dto);
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


    private MemberDto getDtoFromEntity(MemberEntity entity) {
        return MemberCrudInterface.map(entity);
    }


    @Override
    public List<MemberEntity> findEntities(MemberDto dto) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean isExist(MemberDto dto) {
        return memberRepository.findByNick(dto.getNick()) != null;
    }
}
