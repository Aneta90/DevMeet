package pl.com.devmeet.devmeet.member_associated.member.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserDto;
import pl.com.devmeet.devmeet.user.domain.UserEntity;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

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
    public MemberEntity findEntity(MemberDto dto) throws MemberNotFoundException, UserNotFoundException {
        return findMember(dto);
    }

    private MemberEntity findMember(MemberDto memberDto) throws MemberNotFoundException, UserNotFoundException {
        UserEntity userEntity = findUser(memberDto.getUser());
        Optional<MemberEntity> member = memberRepository.findByUser(userEntity);

        if (member.isPresent())
            return member.get();
        else
            throw new MemberNotFoundException(MemberCrudStatusEnum.MEMBER_NOT_FOUND.toString());
    }

    private UserEntity findUser(UserDto dto) throws UserNotFoundException {
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
        } catch (UserNotFoundException | MemberNotFoundException e) {
            return false;
        }
    }
}
