package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.user.domain.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MemberCrudFacadeTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UserRepository userRepository;

    UserDto testUserDto;
    MemberDto testMemberDto;

    private MemberDto createdMemberDto;
    private MemberCrudFacade memberCrudFacade;

    @Before
    public void setUp() throws EntityAlreadyExistsException, EntityNotFoundException {
        testUserDto = new UserDto().builder()
                .email("test@test.pl")
                .phone("221234567")
                .password("testPass")
                .isActive(true)
                .loggedIn(true)
                .build();

        testMemberDto = new MemberDto().builder()
                .user(testUserDto)
                .nick("Wasacz")
                .build();
    }

    private UserCrudFacade initUserCrudFacade() {
        return new UserCrudFacade(userRepository);
    }

    private MemberDto createMember() throws EntityNotFoundException, EntityAlreadyExistsException {
        return initMemberCrudFacade().create(testMemberDto);
    }

    private MemberCrudFacade initMemberCrudFacade() {
        return new MemberCrudFacade(memberRepository, userRepository);
    }

    private UserEntity initTestDB() {
        UserCrudFacade userCrudFacade = initUserCrudFacade();

        return userCrudFacade
                .findEntity(userCrudFacade
                        .create(testUserDto, DefaultUserLoginTypeEnum.EMAIL));
    }

    @Test
    public void INIT_TEST_DB() {
        UserEntity createdUser = initTestDB();
        assertThat(createdUser).isNotNull();
    }

    @Test
    public void WHEN_creating_non_existing_member_THEN_create_new_member() throws EntityAlreadyExistsException, EntityNotFoundException {
        UserEntity createdUser = initTestDB();
        MemberDto createdMember = createMember();

        assertThat(createdMember).isNotNull();
        assertThat(createdMember.getNick()).isEqualTo(testMemberDto.getNick());
        assertThat(createdMember.getUser().getId()).isEqualTo(createdUser.getId());
        assertThat(createdMember.getCreationTime()).isNotNull();
        assertThat(createdMember.isActive()).isTrue();
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void WHEN_try_to_create_existing_member_THEN_throw_exception() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        MemberDto createdMember = createMember();

        MemberDto existingMemberDto = new MemberDto();
        existingMemberDto.setNick("testMember");
        memberCrudFacade.create(existingMemberDto);
    }

    @Test
    public void WHEN_find_existing_member_THEN_return_memberDto() throws EntityNotFoundException, EntityAlreadyExistsException {
        UserEntity createdUser = initTestDB();
        MemberDto createdMember = createMember();

        MemberDto foundMemberDto = memberCrudFacade.read(testMemberDto);

        assertThat(foundMemberDto).isNotNull();
        assertThat(foundMemberDto.getNick()).isEqualTo(testMemberDto.getNick());
        assertThat(foundMemberDto.getUser()).isEqualToComparingFieldByFieldRecursively(testUserDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void WHEN_try_to_find_member_who_does_not_exist_THEN_return_MemberNotFoundException() throws EntityNotFoundException {
        MemberDto notFoundMemberDto = new MemberDto();
        notFoundMemberDto.setNick("null");
        MemberDto memberNotFound = memberCrudFacade.read(notFoundMemberDto);
        assertThat(memberNotFound).isNull();
    }

    @Test
    public void WHEN_member_exists_THEN_return_true() {
        boolean memberExists = memberCrudFacade.isExist(testMemberDto);
        assertThat(memberExists).isTrue();
    }

    @Test
    public void WHEN_member_does_not_exist_THEN_return_false() {
        MemberDto memberNotExisted = new MemberDto();
        testMemberDto.setNick("test");
        boolean memberDoesNotExist = memberCrudFacade.isExist(memberNotExisted);
        assertThat(memberDoesNotExist).isFalse();
    }

    @Test
    public void WHEN_try_to_update_existing_member_THEN_updated_member() throws EntityNotFoundException {
        MemberDto updatedMemberDto = new MemberDto();
        updatedMemberDto.setNick("updatedMember");
        testMemberDto = memberCrudFacade.update(updatedMemberDto, testMemberDto);
        assertThat(testMemberDto.getNick()).isEqualTo("updatedMember");
    }

    @Test(expected = EntityNotFoundException.class)
    public void WHEN_try_to_update_not_existing_member_THEN_return_EntityNotFoundException() throws EntityNotFoundException {
        MemberDto memberNotExisted = new MemberDto();
        testMemberDto.setNick("test");
        MemberDto updatedMemberDto = new MemberDto();
        updatedMemberDto.setNick("updatedMember");
        MemberDto resultMemberDto = memberCrudFacade.update(updatedMemberDto, memberNotExisted);
        assertThat(resultMemberDto).isNull();
    }

    @Test
    public void WHEN_try_to_delete_existing_member_THEN_delete_member() throws EntityNotFoundException { //sprawdzic czy na pewno dobrze działa
        boolean isMemberDeleted = memberCrudFacade.delete(createdMemberDto);
        assertThat(isMemberDeleted).isTrue();
    }

    @Test(expected = EntityNotFoundException.class)
    public void WHEN_try_to_delete_non_existing_member_THEN_throw_EntityNotFoundException() throws EntityNotFoundException {
        MemberDto nonExistingMember = new MemberDto();
        nonExistingMember.setNick("aaa");
        boolean isMemberDeleted = memberCrudFacade.delete(nonExistingMember);
        assertThat(isMemberDeleted).isFalse();
    }
}
