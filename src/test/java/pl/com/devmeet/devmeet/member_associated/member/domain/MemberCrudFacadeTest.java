package pl.com.devmeet.devmeet.member_associated.member.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status.MemberCrudStatusEnum;
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
    private UserCrudFacade userCrudFacade;

    @Before
    public void setUp() {
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

        userCrudFacade = initUserCrudFacade();
        memberCrudFacade = initMemberCrudFacade();
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

    private UserEntity initTestDatabaseByAddingUser() {
        UserCrudFacade userCrudFacade = initUserCrudFacade();

        return userCrudFacade
                .findEntity(userCrudFacade
                        .create(testUserDto, DefaultUserLoginTypeEnum.EMAIL));
    }

    @Test
    public void INIT_TEST_DB() {
        UserEntity createdUser = initTestDatabaseByAddingUser();
        assertThat(createdUser).isNotNull();
    }

    @Test
    public void WHEN_creating_non_existing_member_THEN_create_new_member() throws EntityAlreadyExistsException, EntityNotFoundException {
        UserEntity createdUser = initTestDatabaseByAddingUser();
        MemberDto member = createMember();

        assertThat(member).isNotNull();

        assertThat(member.getUser().getId()).isEqualTo(createdUser.getId());
        assertThat(member.getUser().isActive()).isTrue();

        assertThat(member.getNick()).isEqualTo(testMemberDto.getNick());
        assertThat(member.getCreationTime()).isNotNull();
        assertThat(member.isActive()).isTrue();
    }

    @Test
    public void WHEN_try_to_create_existing_member_THEN_throw_exception() {
        initTestDatabaseByAddingUser();
        MemberDto createdMember = null;

        try {
            createdMember = createMember();
        } catch (EntityAlreadyExistsException | EntityNotFoundException e) {
            Assert.fail();
        }

        try {
            memberCrudFacade.create(createdMember);
            Assert.fail();
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(EntityAlreadyExistsException.class)
                    .hasMessage(MemberCrudStatusEnum.MEMBER_ALREADY_EXIST.toString());
        }
    }

    @Test
    public void WHEN_find_existing_member_THEN_return_memberDto() throws EntityNotFoundException, EntityAlreadyExistsException {
        initTestDatabaseByAddingUser();
        createMember();

        MemberDto foundMemberDto = memberCrudFacade.read(testMemberDto);

        assertThat(foundMemberDto).isNotNull();
        assertThat(foundMemberDto.getNick()).isEqualTo(testMemberDto.getNick());
        assertThat(foundMemberDto.getUser()).isNotNull();
    }

    @Test
    public void WHEN_try_to_find_member_who_does_not_exist_THEN_return_MemberNotFoundException() {
        initTestDatabaseByAddingUser();

        try {
            memberCrudFacade.read(testMemberDto);
            Assert.fail();
        } catch (EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(MemberCrudStatusEnum.MEMBER_USER_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_member_exists_THEN_return_true() {
        initTestDatabaseByAddingUser();
        try {
            createMember();
        } catch (EntityAlreadyExistsException | EntityNotFoundException e) {
            e.printStackTrace();
        }

        boolean memberExists = memberCrudFacade.isExist(testMemberDto);
        assertThat(memberExists).isTrue();
    }

    @Test
    public void WHEN_member_does_not_exist_THEN_return_false() {
        boolean memberDoesNotExist = memberCrudFacade.isExist(testMemberDto);
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
