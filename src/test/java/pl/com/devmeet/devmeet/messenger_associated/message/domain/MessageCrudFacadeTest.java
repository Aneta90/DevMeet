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
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerCrudFacade;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeet.user.domain.*;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MessageCrudFacadeTest {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessengerRepository messengerRepository;
    @Autowired
    GroupCrudRepository groupCrudRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UserRepository userRepository;

    private UserCrudFacade userCrudFacade;
    private MemberCrudFacade memberCrudFacade;
    private GroupCrudFacade groupCrudFacade;
    private MessengerCrudFacade messengerCrudFacade;
    private MessageCrudFacade messageCrudFacade;

    private GroupDto testGroupAndReceiverGroup;
    private MessengerDto groupsReceiverMessenger;

    private UserDto firstUser;
    private MemberDto memberSender;
    private MessengerDto membersSenderMessenger;

    private UserDto secondUser;
    private MemberDto memberReceiver;
    private MessengerDto membersReceiverMessenger;

    private MessageDto messageMemberToMember;
    private MessageDto messageMemberToGroup;

    @Before
    public void setUp() {

        initSenderMember();
        initReceiverMember();
        initReceiverGroup();

        messageMemberToMember = MessageDto.builder()
                .creationTime(DateTime.now())
                .sender(membersSenderMessenger)
                .receiver(membersReceiverMessenger)
                .message("test message from member to member")
                .build();

        messageMemberToGroup = MessageDto.builder()
                .creationTime(DateTime.now())
                .sender(membersSenderMessenger)
                .receiver(groupsReceiverMessenger)
                .message("test message from member to group (group chat feature)")
                .build();
    }

    private void initSenderMember() {
        MemberSenderInitiator senderInitiator = MemberSenderInitiator.builder()
                .userRepository(userRepository)
                .memberRepository(memberRepository)
                .messengerRepository(messengerRepository)
                .build();
        senderInitiator.init();

        this.firstUser = senderInitiator.getUserDto();
        this.memberSender = senderInitiator.getMemberDto();
        this.membersSenderMessenger = senderInitiator.getMessengerDto();
    }

    private void initReceiverMember() {
        MemberReceiverInitiator receiverInitiator = MemberReceiverInitiator.builder()
                .userRepository(userRepository)
                .memberRepository(memberRepository)
                .messengerRepository(messengerRepository)
                .build();
        receiverInitiator.init();

        this.secondUser = receiverInitiator.getUserDto();
        this.memberReceiver = receiverInitiator.getMemberDto();
        this.membersReceiverMessenger = receiverInitiator.getMessengerDto();
    }

    private void initReceiverGroup() {
        TestGroupAndGroupReceiverInitiator testGroupAndGroupReceiverInitiator = TestGroupAndGroupReceiverInitiator.builder()
                .groupRepository(groupCrudRepository)
                .messengerRepository(messengerRepository)
                .build();
        testGroupAndGroupReceiverInitiator.init();

        this.testGroupAndReceiverGroup = testGroupAndGroupReceiverInitiator.getGroupDto();
        this.groupsReceiverMessenger = testGroupAndGroupReceiverInitiator.getMessengerDto();
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

    private MessengerCrudFacade initMessengerCrudFacade(){
        return new MessengerCrudFacade(messengerRepository);
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
        messengerCrudFacade = initMessengerCrudFacade();
        messageCrudFacade = initMessageCrudFacade();

        UserEntity userEntityFirst = userCrudFacade.findEntity(userCrudFacade.create(firstUser, DefaultUserLoginTypeEnum.EMAIL));
        UserEntity userEntitySecond = userCrudFacade.findEntity(userCrudFacade.create(secondUser, DefaultUserLoginTypeEnum.EMAIL));

        MemberEntity memberEntityFirst = null;
        try {
            memberEntityFirst = memberCrudFacade.findEntity(memberCrudFacade.create(memberSender));
        } catch (MemberNotFoundException | MemberAlreadyExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }

        MemberEntity memberEntitySecond = null;
        try {
            memberEntitySecond = memberCrudFacade.findEntity(memberCrudFacade.create(memberReceiver));
        } catch (MemberNotFoundException | MemberAlreadyExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }

        GroupEntity groupEntity = null;
        try {
            groupEntity = groupCrudFacade.findEntity(groupCrudFacade.create(testGroupAndReceiverGroup));
        } catch (GroupNotFoundException | GroupAlreadyExistsException e) {
            e.printStackTrace();
        }

        return userEntityFirst != null
                && userEntitySecond != null
                && memberEntityFirst != null
                && memberEntitySecond != null
                && groupEntity != null;
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
        MessageDto messageEntity = messageCrudFacade.create(messageMemberToMember);

        assertThat(messageEntity).isNotNull();
        assertThat(messageEntity.getMessage()).isEqualTo("testMessagee");
        assertThat(messageEntity.getCreationTime()).isNotNull();
//        assertThat(messageEntity.getFromMember()).isEqualTo(memberSender);
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