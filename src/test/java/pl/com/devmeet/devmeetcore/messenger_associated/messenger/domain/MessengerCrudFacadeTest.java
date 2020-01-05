package pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupAlreadyExistsException;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberAlreadyExistsException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerInfoStatusEnum;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.*;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyActiveException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyExistsException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MessengerCrudFacadeTest {

    @Autowired
    private MessengerRepository messengerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GroupCrudRepository groupRepository;

    private MessengerDto firstTestMemberMessenger;
    private MessengerDto secondTestMemberMessenger;
    private MessengerDto firstTestGroupMessenger;
    private MessengerDto secondTestGroupMessenger;

    private UserDto firstTestUserDto;
    private MemberDto firstTestMemberDto;
    private GroupDto firstTestGroupDto;

    @Before
    public void setUp() {

        this.firstTestUserDto = UserDto.builder()
                .email("test1@test1.pl")
                .password("testPass1")
                .build();

        this.firstTestMemberDto = MemberDto.builder()
                .user(firstTestUserDto)
                .nick("testMember2")
                .build();

        this.firstTestGroupDto = GroupDto.builder()
                .groupName("Java test group")
                .website("www.testWebsite.com")
                .description("Welcome to test group")
                .membersLimit(5)
                .memberCounter(6)
                .meetingCounter(1)
                .build();

        this.firstTestMemberMessenger = MessengerDto.builder()
                .member(firstTestMemberDto)
                .build();

        this.firstTestGroupMessenger = MessengerDto.builder()
                .group(firstTestGroupDto)
                .build();

    }

    private MessengerCrudFacade initMessengerFacade() {
        return new MessengerCrudFacade(messengerRepository, userRepository, memberRepository, groupRepository);
    }

    private UserCrudFacade initUseFacade() {
        return new UserCrudFacade(userRepository);
    }

    private MemberCrudFacade initMemberFacade() {
        return new MemberCrudFacade(memberRepository, userRepository, messengerRepository, groupRepository);
    }

    private GroupCrudFacade initGroupFacade() {
        return new GroupCrudFacade(groupRepository, memberRepository, userRepository, messengerRepository);
    }

    private boolean initTestDB() {
        UserCrudFacade userCrudFacade = initUseFacade();
        MemberCrudFacade memberCrudFacade = initMemberFacade();
        GroupCrudFacade groupCrudFacade = initGroupFacade();


        try {
            userCrudFacade.activation(
                    userCrudFacade.add(firstTestUserDto));
        } catch (UserAlreadyActiveException | UserAlreadyExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }
        UserEntity userEntityFirst = null;
        try {
            userEntityFirst = userCrudFacade.findEntityByEmail(firstTestUserDto);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        MemberEntity memberEntityFirst = null;
        try {
            memberEntityFirst = memberCrudFacade.findEntity(memberCrudFacade.add(firstTestMemberDto));
        } catch (MemberNotFoundException | MemberAlreadyExistsException | UserNotFoundException | GroupNotFoundException | MessengerAlreadyExistsException | MessengerArgumentNotSpecified e) {
            e.printStackTrace();
        }

        GroupEntity groupEntity = null;
        try {
            groupEntity = groupCrudFacade.findEntityByGroup(groupCrudFacade.add(firstTestGroupDto));
        } catch (GroupNotFoundException | GroupAlreadyExistsException | UserNotFoundException | MemberNotFoundException | MessengerAlreadyExistsException | MessengerArgumentNotSpecified e) {
            e.printStackTrace();
        }

        return userEntityFirst != null
                && memberEntityFirst != null
                && groupEntity != null;
    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        Assertions.assertThat(initDB).isTrue();
    }

    @Test
    public void WHEN_create_MEMBER_THEN_create_messenger_for_MEMBER_and_return_create_new_messenger() throws UserNotFoundException, GroupNotFoundException, MemberNotFoundException, MessengerNotFoundException {
        initTestDB();
        MessengerDto created = initMessengerFacade().findByMember(firstTestMemberDto);

        assertThat(created).isNotNull();

        assertThat(created.getGroup()).isNull();
        assertThat(created.getMember()).isNotNull();

        assertThat(created.getCreationTime()).isNotNull();
        assertThat(created.isActive()).isTrue();
    }

    @Test
    public void WHEN_create_GROUP_THEN_create_messenger_for_GROUP_and_return_create_new_messenger() throws UserNotFoundException, GroupNotFoundException, MemberNotFoundException, MessengerNotFoundException {
        initTestDB();
        MessengerDto created = initMessengerFacade().findByGroup(firstTestGroupDto);

        assertThat(created).isNotNull();

        assertThat(created.getGroup()).isNotNull();
        assertThat(created.getMember()).isNull();

        assertThat(created.getCreationTime()).isNotNull();
        assertThat(created.isActive()).isTrue();
    }

    @Test
    public void WHEN_try_to_create_existing_messenger_for_MEMBER_THEN_return_MessengerAlreadyExistsException() {
        initTestDB();
        MessengerCrudFacade messengerCrudFacade = initMessengerFacade();

        try {
            messengerCrudFacade.addToMember(firstTestMemberDto);
            Assert.fail();

        } catch (MessengerArgumentNotSpecified | MemberNotFoundException | UserNotFoundException | GroupNotFoundException ex) {
            Assert.fail();

        } catch (MessengerAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(MessengerAlreadyExistsException.class)
                    .hasMessage(MessengerInfoStatusEnum.MESSENGER_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_try_to_create_existing_messenger_for_GROUP_THEN_return_MessengerAlreadyExistsException() {
        initTestDB();
        MessengerCrudFacade messengerCrudFacade = initMessengerFacade();

        try {
            messengerCrudFacade.addToGroup(firstTestGroupDto);
            Assert.fail();

        } catch (MessengerArgumentNotSpecified | MemberNotFoundException | UserNotFoundException | GroupNotFoundException ex) {
            Assert.fail();

        } catch (MessengerAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(MessengerAlreadyExistsException.class)
                    .hasMessage(MessengerInfoStatusEnum.MESSENGER_ALREADY_EXISTS.toString());
        }
    }

    @Test
    public void WHEN_found_messenger_for_MEMBER_THEN_return_messenger() throws UserNotFoundException, GroupNotFoundException, MessengerNotFoundException, MemberNotFoundException {
        initTestDB();
        MessengerDto messengerDto = initMessengerFacade().findByMember(firstTestMemberDto);

        assertThat(messengerDto).isNotNull();
    }

    @Test
    public void WHEN_found_messenger_for_GROUP_THEN_return_messenger() throws UserNotFoundException, GroupNotFoundException, MessengerNotFoundException, MemberNotFoundException {
        initTestDB();
        MessengerDto messengerDto = initMessengerFacade().findByGroup(firstTestGroupDto);

        assertThat(messengerDto).isNotNull();
    }

    @Test
    public void WHEN_try_to_find_MEMBER_messenger_which_does_not_exist_THEN_return_MessengerNotFoundException() {
        initTestDB();

        try {
            initMessengerFacade().findByMember(firstTestMemberDto);

        } catch (GroupNotFoundException | UserNotFoundException | MemberNotFoundException e) {
            e.printStackTrace();

        } catch (
                MessengerNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(MessengerNotFoundException.class)
                    .hasMessage(MessengerInfoStatusEnum.MESSENGER_NOT_FOUND_BY_MEMBER.toString());
        }
    }

    @Test
    public void WHEN_try_to_find_GROUP_messenger_which_does_not_exist_THEN_return_MessengerNotFoundException() {
        initTestDB();

        try {
            initMessengerFacade().findByGroup(firstTestGroupDto);

        } catch (GroupNotFoundException | UserNotFoundException | MemberNotFoundException e) {
            e.printStackTrace();

        } catch (MessengerNotFoundException e) {
            assertThat(e)
                    .isInstanceOf(MessengerNotFoundException.class)
                    .hasMessage(MessengerInfoStatusEnum.MESSENGER_NOT_FOUND_BY_GROUP.toString());
        }
    }

    @Test
    public void WHEN_delete_existing_MEMBER_messenger_THEN_delete_messenger() throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException {
        initTestDB();
        MessengerCrudFacade messengerCrudFacade = initMessengerFacade();
        MessengerDto deleted;

        deleted = messengerCrudFacade.deactivateMembersMessenger(firstTestMemberDto);

        assertThat(deleted).isNotNull();
        assertThat(deleted.isActive()).isFalse();
    }

    @Test
    public void WHEN_delete_existing_GROUP_messenger_THEN_delete_messenger() throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException, MessengerAlreadyExistsException {
        initTestDB();
        MessengerCrudFacade messengerCrudFacade = initMessengerFacade();
        MessengerDto deleted;

        deleted = messengerCrudFacade.deactivateGroupsMessenger(firstTestGroupDto);

        assertThat(deleted).isNotNull();
        assertThat(deleted.isActive()).isFalse();
    }

    @Test
    public void WHEN_try_to_delete_non_existing_MEMBER_messenger_then_throw_EntityNotFoundException() {
        initTestDB();
        MessengerCrudFacade messengerCrudFacade = initMessengerFacade();

        try {
            messengerCrudFacade.delete(firstTestMemberMessenger);

        } catch (UserNotFoundException | MemberNotFoundException | GroupNotFoundException | MessengerNotFoundException | MessengerAlreadyExistsException e) {
            Assert.fail();
        }

        try {
            messengerCrudFacade.delete(firstTestMemberMessenger);
            Assert.fail();

        } catch (MessengerNotFoundException | UserNotFoundException | MemberNotFoundException | GroupNotFoundException e) {
            Assert.fail();

        } catch (MessengerAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(MessengerAlreadyExistsException.class)
                    .hasMessage(MessengerInfoStatusEnum.MESSENGER_FOUND_BUT_NOT_ACTIVE.toString());
        }
    }

    @Test
    public void WHEN_try_to_delete_non_existing_GROUP_messenger_then_throw_EntityNotFoundException() {
        initTestDB();
        MessengerCrudFacade messengerCrudFacade = initMessengerFacade();

        try {
            messengerCrudFacade.delete(firstTestGroupMessenger);
        } catch (UserNotFoundException | MemberNotFoundException | GroupNotFoundException | MessengerNotFoundException | MessengerAlreadyExistsException e) {
            Assert.fail();
        }

        try {
            messengerCrudFacade.delete(firstTestGroupMessenger);
            Assert.fail();

        } catch (MessengerNotFoundException | UserNotFoundException | MemberNotFoundException | GroupNotFoundException e) {
            Assert.fail();

        } catch (MessengerAlreadyExistsException e) {
            assertThat(e)
                    .isInstanceOf(MessengerAlreadyExistsException.class)
                    .hasMessage(MessengerInfoStatusEnum.MESSENGER_FOUND_BUT_NOT_ACTIVE.toString());
        }
    }
}