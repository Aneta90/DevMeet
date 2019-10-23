package pl.com.devmeet.devmeet.group_associated.permission.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.domain_utils.CrudErrorEnum;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.permission.domain.status_and_exceptions.PermissionCrudStatusEnum;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.user.domain.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)

public class PermissionCrudFacadeTest {

    @Autowired
    private PermissionCrudRepository permissionCrudRepository;
    @Autowired
    private GroupCrudRepository groupCrudRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userRepository;

    private PermissionCrudFacade permissionCrudFacade;
    private GroupCrudFacade groupCrudFacade;
    private MemberCrudFacade memberCrudFacade;
    private UserCrudFacade userCrudFacade;

    private GroupDto testGroupDto;
    private MemberDto testMemberDto;
    private UserDto testUserDto;
    private PermissionDto testPermissionDto;

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
                .nick("WasatyJanusz")
                .build();

        testGroupDto = new GroupDto().builder()
                .groupName("Java test group")
                .website("www.testWebsite.com")
                .description("Welcome to test group")
//                .messenger(entity.getMessenger())
                .membersLimit(5)
                .memberCounter(6)
                .meetingCounter(1)
                .creationTime(null)
                .modificationTime(null)
                .isActive(false)
                .build();

        testPermissionDto = new PermissionDto().builder()
                .member(testMemberDto)
                .group(testGroupDto)
                .possibleToVote(true)
                .possibleToMessaging(true)
                .possibleToChangeGroupName(true)
                .possibleToBanMember(true)
                .memberBaned(false)
                .isActive(false)
                .build();
    }

    private UserCrudFacade initUserCrudFacade() {
        return new UserCrudFacade(userRepository);
    }

    private GroupCrudFacade initGroupCrudFacade() {
        return new GroupCrudFacade(groupCrudRepository);
    }

    private MemberCrudFacade initMemberCrudFacade() {
        return new MemberCrudFacade(memberRepository);
    }

    private PermissionCrudFacade initPermissionCrudFacade() {
        return new PermissionCrudFacade(permissionCrudRepository, groupCrudRepository, memberRepository);
    }

    private boolean initTestDB() {
        userCrudFacade = initUserCrudFacade();
        groupCrudFacade = initGroupCrudFacade();
        memberCrudFacade = initMemberCrudFacade();

        UserEntity testUser = userCrudFacade
                .findEntity(userCrudFacade
                        .create(testUserDto, DefaultUserLoginTypeEnum.PHONE));

        GroupEntity groupEntity;
        try {
            groupEntity = groupCrudFacade
                    .findEntity(groupCrudFacade
                            .create(testGroupDto));
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            groupEntity = null;
        }

        MemberEntity memberEntity;
        try {
            memberEntity = memberCrudFacade
                    .findEntity(memberCrudFacade
                            .create(testMemberDto));
        } catch (EntityNotFoundException e) {
            memberEntity = null;
        } catch (EntityAlreadyExistsException e) {
            memberEntity = null;
        }

        return testUser != null
                && groupEntity != null
                && memberEntity != null;
    }

    @Test
    public void USER_CRUD_FACADE_WR() {
        UserCrudFacade userFacade = initUserCrudFacade();
        UserDto testUser = userFacade.create(testUserDto, DefaultUserLoginTypeEnum.PHONE);
        UserEntity userEntity = userFacade.findEntity(testUser);

        assertThat(userEntity).isNotNull();
    }

    @Test
    public void GROUP_CRUD_FACADE_WR() throws EntityAlreadyExistsException, EntityNotFoundException {
        GroupCrudFacade groupFacade = initGroupCrudFacade();
        GroupEntity groupEntity = groupFacade.findEntity(groupFacade.create(testGroupDto));

        assertThat(groupEntity).isNotNull();
    }

    @Test
    public void MEMBER_CRUD_FACADE_WR() throws EntityAlreadyExistsException, EntityNotFoundException {
        MemberCrudFacade memberFacade = initMemberCrudFacade();
        MemberEntity memberEntity = memberFacade.findEntity(memberFacade.create(testMemberDto));

        assertThat(memberEntity).isNotNull();
    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        assertThat(initDB).isTrue();
    }

    @Test
    public void WHEN_create_not_existing_permission_to_group_THEN_return_permission() throws EntityAlreadyExistsException, EntityNotFoundException {
        PermissionDto created;
        initTestDB();

        permissionCrudFacade = initPermissionCrudFacade();

        created = permissionCrudFacade.create(testPermissionDto);

        assertThat(created.getMember()).isNotNull();
        assertThat(created.getGroup()).isNotNull();

        assertThat(created.isPossibleToVote()).isTrue();
        assertThat(created.isPossibleToMessaging()).isTrue();
        assertThat(created.isPossibleToChangeGroupName()).isTrue();
        assertThat(created.isPossibleToBanMember()).isTrue();

        assertThat(created.getCreationTime()).isNotNull();
        assertThat(created.getModificationTime()).isNull();
        assertThat(created.isActive()).isTrue();
    }

    @Test
    public void WHEN_try_to_create_existing_permission_to_group_THEN_EntityAlreadyExistsException() {
        initTestDB();
        permissionCrudFacade = initPermissionCrudFacade();
        try {
            permissionCrudFacade.create(testPermissionDto);
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            Assert.fail();
        }

        try {
            permissionCrudFacade.create(testPermissionDto);
            Assert.fail();
        } catch (EntityNotFoundException e) {
            Assert.fail();
        } catch (EntityAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(EntityAlreadyExistsException.class)
                    .hasMessage(PermissionCrudStatusEnum.PERMISSION_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_found_permission_THEN_return_permission() throws EntityNotFoundException, EntityAlreadyExistsException {
        initTestDB();
        PermissionDto created;
        PermissionDto found = null;
        permissionCrudFacade = initPermissionCrudFacade();

        created = permissionCrudFacade.create(testPermissionDto);

        found = permissionCrudFacade.read(testPermissionDto);


        assertThat(found).isNotNull();
    }

    @Test
    public void WHEN_try_to_find_not_existing_permission_THEN_return_EntityNotFoundException() {
        initTestDB();
        permissionCrudFacade = initPermissionCrudFacade();
        try {
            permissionCrudFacade.read(testPermissionDto);
            Assert.fail();
        } catch (EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(PermissionCrudStatusEnum.PERMISSION_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_try_to_find_all_permissions_THEN_return_UnsupportedOperationException() {
        PermissionCrudFacade permissionCrudFacade = initPermissionCrudFacade();
        try {
            permissionCrudFacade.readAll(testPermissionDto);
        } catch (UnsupportedOperationException e) {
            assertThat(e)
                    .isInstanceOf(UnsupportedOperationException.class)
                    .hasMessage(PermissionCrudStatusEnum.METHOD_NOT_IMPLEMENTED.toString());
        }
    }

    @Test
    public void WHEN_update_existing_permission_THEN_return_permission() throws EntityAlreadyExistsException, EntityNotFoundException {
        initTestDB();
        PermissionCrudFacade permissionCrudFacade = initPermissionCrudFacade();
        PermissionDto created = permissionCrudFacade.create(testPermissionDto);
        PermissionDto updated = permissionCrudFacade.update(testPermissionDto, permissionUpdatedValues(testPermissionDto));

        assertThat(updated.isPossibleToVote()).isFalse();
        assertThat(updated.isPossibleToMessaging()).isFalse();
        assertThat(updated.isPossibleToChangeGroupName()).isFalse();
        assertThat(updated.isPossibleToBanMember()).isFalse();

        assertThat(updated.isMemberBaned()).isTrue();

        assertThat(updated.getMember()).isEqualToComparingFieldByField(created.getMember());
        assertThat(updated.getGroup()).isEqualToComparingFieldByField(created.getGroup());
        assertThat(updated.getCreationTime()).isEqualTo(created.getCreationTime());
        assertThat(updated.getModificationTime()).isNotEqualTo(created.getModificationTime());
        assertThat(updated.isActive()).isEqualTo(created.isActive());
    }

    private PermissionDto permissionUpdatedValues(PermissionDto permission) {
        permission.setPossibleToVote(false);
        permission.setPossibleToMessaging(false);
        permission.setPossibleToChangeGroupName(false);
        permission.setPossibleToBanMember(false);

        permission.setMemberBaned(true);
        return permission;
    }

    @Test
    public void WHEN_try_to_update_not_existing_permission_THEN_return_EntityNotFoundException() {
        initTestDB();
        PermissionCrudFacade permissionCrudFacade = initPermissionCrudFacade();
        try {
            permissionCrudFacade.update(testPermissionDto, permissionUpdatedValues(testPermissionDto));
        } catch (EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(PermissionCrudStatusEnum.PERMISSION_NOT_FOUND.toString());
        }
    }

    @Test
    public void WHEN_delete_existing_permission_THEN_return_permission() throws EntityNotFoundException, EntityAlreadyExistsException {
        initTestDB();
        PermissionCrudFacade permissionCrudFacade = initPermissionCrudFacade();
        PermissionDto created = permissionCrudFacade.create(testPermissionDto);
        PermissionDto deleted = permissionCrudFacade.delete(testPermissionDto);

        assertThat(deleted).isNotNull();
        assertThat(deleted.isActive()).isNotEqualTo(created.isActive());
        assertThat(deleted.getCreationTime()).isEqualTo(created.getCreationTime());
        assertThat(deleted.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_delete_not_existing_permission_THEN_return_EntityNotFoundException() throws EntityAlreadyExistsException {
        initTestDB();
        PermissionCrudFacade permissionCrudFacade = initPermissionCrudFacade();

        try {
            permissionCrudFacade.delete(testPermissionDto);
        } catch (EntityNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(PermissionCrudStatusEnum.PERMISSION_NOT_FOUND.toString());
        }
    }
}