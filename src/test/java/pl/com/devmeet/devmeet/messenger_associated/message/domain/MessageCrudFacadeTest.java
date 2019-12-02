package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
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
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.*;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    int numberOfTestMessages = 10;

    @Before
    public void setUp() {

        initSenderMember();
        initReceiverMember();
        initReceiverGroup();

    }

    private void initSenderMember() {
        MemberSenderModel senderInitiator = MemberSenderModel.builder()
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
        MemberReceiverModel receiverInitiator = MemberReceiverModel.builder()
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
        TestGroupForMembersAndGroupReceiverInitiator testGroupForMembersAndGroupReceiverInitiator = TestGroupForMembersAndGroupReceiverInitiator.builder()
                .groupRepository(groupCrudRepository)
                .messengerRepository(messengerRepository)
                .build();
        testGroupForMembersAndGroupReceiverInitiator.init();

        this.testGroupAndReceiverGroup = testGroupForMembersAndGroupReceiverInitiator.getGroupDto();
        this.groupsReceiverMessenger = testGroupForMembersAndGroupReceiverInitiator.getMessengerDto();
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

    private MessengerCrudFacade initMessengerCrudFacade() {
        return new MessengerCrudFacade(messengerRepository, userRepository, memberRepository, groupCrudRepository);
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

    private List<MessageDto> saveMessagesInToDb(MessengerDto sender, MessengerDto receiver, int numberOfTestMessages) throws UserNotFoundException, MessengerNotFoundException, MemberNotFoundException, GroupNotFoundException {
        List<MessageDto> result = new ArrayList<>();
        MessageCrudFacade messageCrudFacade = initMessageCrudFacade();
        List<MessageDto> messagesToSave = initTestMessagesGenerator(sender, receiver, numberOfTestMessages);

        for (MessageDto message : messagesToSave) {
            result.add(messageCrudFacade.create(message));
        }

        return result;
    }

    private List<MessageDto> initTestMessagesGenerator(MessengerDto sender, MessengerDto receiver, int numberOfTestMessages) {
        return new TestMessagesGenerator()
                .generate(sender, receiver, numberOfTestMessages);
    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        assertThat(initDB).isTrue();
    }

    @Test
    public void create_new_message() throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException {
        initTestDB();
        int numberOfMessagesLocal = 1;
        List<MessageDto> createdMessages = saveMessagesInToDb(membersSenderMessenger, membersReceiverMessenger, numberOfMessagesLocal);

        assertThat(createdMessages.size()).isEqualTo(numberOfMessagesLocal);
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getSender().getMember().getNick()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getReceiver().getMember().getNick()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getMessage()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getCreationTime()).isNotNull());
    }

    @Ignore
    @Test
    public void findEntityFromMember() {
    }

    @Ignore
    @Test
    public void findEntityToMember() {
    }

    @Ignore
    @Test
    public void update() {
    }

    @Ignore
    @Test
    public void deleteMessagesSentToMember() {
    }

    @Ignore
    @Test
    public void deleteMessagesSentFromMember() {
    }

    @Ignore
    @Test
    public void map() {
    }

    @Ignore
    @Test
    public void map1() {
    }
}