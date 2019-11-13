package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status.MemberCrudStatusEnum;
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
        return findMember(dto);
    }

    private MemberEntity findMember(MemberDto memberDto) throws EntityNotFoundException {
        UserEntity userEntity = findUser(memberDto.getUser());
        Optional<MemberEntity> member = memberRepository.findByUser(userEntity);

        if (member.isPresent())
            return member.get();
        else
            throw new EntityNotFoundException(MemberCrudStatusEnum.MEMBER_USER_NOT_FOUND.toString());
    }

    private UserEntity findUser(UserDto dto) throws EntityNotFoundException {
        return userFinder.findUserEntity(dto);
    }

    @Override
    public List<MemberEntity> findEntities(MemberDto dto) {
        return null;
    }

    @Override
    public boolean isExist(MemberDto dto) {
        try {
            findMember(dto);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
