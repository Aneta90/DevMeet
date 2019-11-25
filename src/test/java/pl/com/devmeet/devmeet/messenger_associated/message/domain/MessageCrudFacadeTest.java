package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupAlreadyExistsException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberAlreadyExistsException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.user.domain.*;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MessageCrudFacadeTest {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    GroupCrudRepository groupCrudRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UserRepository userRepository;

    private UserCrudFacade userCrudFacade;
    private MemberCrudFacade memberCrudFacade;
    private GroupCrudFacade groupCrudFacade;
    private MessageCrudFacade messageCrudFacade;

    private GroupDto testGroup;
    private MemberDto memberDto;
    private MemberDto memberDto1;
    private UserDto userDto;
    private UserDto userDto1;
    private MessageDto messageDto;

    @Before
    public void setUp() {

        testGroup = new GroupDto().builder()
                .groupName("Java test group")
                .website("www.testWebsite.com")
                .description("Welcome to test group")
                .messenger(null)
                .membersLimit(5)
                .memberCounter(6)
                .meetingCounter(1)
                .creationTime(null)
                .modificationTime(null)
                .isActive(false)
                .build();

        userDto = new UserDto().builder()
                .email("test@test.pl")
                .phone("221234567")
                .password("testPass")
                .isActive(true)
                .loggedIn(true)
                .build();

        userDto1 = new UserDto().builder()
                .email("test1@test1.pl")
                .phone("221234567")
                .password("testPass1")
                .isActive(true)
                .loggedIn(true)
                .build();

        memberDto = new MemberDto().builder()
                .user(userDto)
                .nick("testMember")
                .isActive(true)
                .modificationTime(DateTime.now())
                .creationTime(DateTime.now())
                .build();

        memberDto1 = new MemberDto().builder()
                .user(userDto1)
                .nick("testMember2")
                .isActive(true)
                .modificationTime(DateTime.now())
                .creationTime(DateTime.now())
                .build();

        messageDto = new MessageDto().builder()
                .creationTime(DateTime.now())
                .fromMember(memberDto)
                .toMember(memberDto1)
                .toGroup(testGroup)
                .message("testMessagee")
                .build();
    }

    private UserCrudFacade initUserCrudFacade() {
        return new UserCrudFacade(userRepository);
    }

    private MemberCrudFacade initMemberCrudFacade() {
        return new MemberCrudFacade(memberRepository, userRepository);
    }

    private GroupCrudFacade initGroupCrudFacade() {
        return new GroupCrudFacade(groupCrudRepository);
    }

    private MessageCrudFacade initMessageCrudFacade() {
        return new MessageCrudFacade(
                messageRepository,
                groupCrudRepository,
                memberRepository,
                userRepository);
    }

    private boolean initTestDB() {
        userCrudFacade = initUserCrudFacade();
        memberCrudFacade = initMemberCrudFacade();
        groupCrudFacade = initGroupCrudFacade();
        messageCrudFacade = initMessageCrudFacade();

        UserEntity userEntityFirst = userCrudFacade.findEntity(userCrudFacade.create(userDto, DefaultUserLoginTypeEnum.EMAIL));
        UserEntity userEntitySecond = userCrudFacade.findEntity(userCrudFacade.create(userDto1, DefaultUserLoginTypeEnum.EMAIL));

        MemberEntity memberEntityFirst = null;
        try {
            memberEntityFirst = memberCrudFacade.findEntity(memberCrudFacade.create(memberDto));
        } catch (MemberNotFoundException | MemberAlreadyExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }

        MemberEntity memberEntitySecond = null;
        try {
            memberEntitySecond = memberCrudFacade.findEntity(memberCrudFacade.create(memberDto1));
        } catch (MemberNotFoundException | MemberAlreadyExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }

        GroupEntity groupEntity = null;
        try {
            groupEntity = groupCrudFacade.findEntity(groupCrudFacade.create(testGroup));
        } catch (GroupNotFoundException | GroupAlreadyExistsException e) {
            e.printStackTrace();
        }

        List<MessageEntity> messageEntity;

        messageEntity = messageCrudFacade.findEntityFromMember(messageCrudFacade.create(messageDto).getFromMember().getNick());

        return userEntityFirst != null
                && userEntitySecond != null
                && memberEntityFirst != null
                && memberEntitySecond != null
                && groupEntity != null
                && messageEntity != null;

    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        assertThat(initDB).isTrue();
    }

    @Test
    public void create_new_message() {

        initTestDB();
        MessageCrudFacade messageCrudFacade = initMessageCrudFacade();
        MessageDto messageEntity = messageCrudFacade.create(messageDto);

        assertThat(messageEntity).isNotNull();
        assertThat(messageEntity.getMessage()).isEqualTo("testMessagee");
        assertThat(messageEntity.getCreationTime()).isNotNull();
        assertThat(messageEntity.getFromMember()).isEqualTo(memberDto);
    }

    @Test
    public void findEntityFromMember() {
    }

    @Test
    public void findEntityToMember() {
    }

    @Test
    public void update() {
    }

    @Test
    public void deleteMessagesSentToMember() {
    }

    @Test
    public void deleteMessagesSentFromMember() {
    }

    @Test
    public void map() {
    }

    @Test
    public void map1() {
    }
}