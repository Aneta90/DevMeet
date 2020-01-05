package pl.com.devmeet.devmeetcore.messenger_associated.message.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;
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
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageArgumentNotSpecifiedException;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageCrudStatusEnum;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageFoundButNotActiveException;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerCrudFacade;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.*;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyExistsException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

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

    private String standardTestMessageText = "Standard test message text.";
//    int numberOfTestMessages = 3; // <-- better if this parameter is smaller than 10

    @Before
    public void setUp() {

        initSenderMember();
        initReceiverMember();
        initReceiverGroup();

    }

    private void initSenderMember() {
        MemberSenderBuilder senderBuilder = MemberSenderBuilder.builder()
                .userRepository(userRepository)
                .memberRepository(memberRepository)
                .messengerRepository(messengerRepository)
                .build();

        senderBuilder.build();

        this.firstUser = senderBuilder.getUserDto();
        this.memberSender = senderBuilder.getMemberDto();
        this.membersSenderMessenger = senderBuilder.getMessengerDto();
    }

    private void initReceiverMember() {
        MemberReceiverBuilder receiverBuilder = MemberReceiverBuilder.builder()
                .userRepository(userRepository)
                .memberRepository(memberRepository)
                .messengerRepository(messengerRepository)
                .build();

        receiverBuilder.build();

        this.secondUser = receiverBuilder.getUserDto();
        this.memberReceiver = receiverBuilder.getMemberDto();
        this.membersReceiverMessenger = receiverBuilder.getMessengerDto();
    }

    private void initReceiverGroup() {
        TestGroupForMembersAndGroupReceiverBuilder testGroupForMembersAndGroupReceiverBuilder = TestGroupForMembersAndGroupReceiverBuilder.builder()
                .groupRepository(groupCrudRepository)
                .messengerRepository(messengerRepository)
                .build();

        testGroupForMembersAndGroupReceiverBuilder.build();

        this.testGroupAndReceiverGroup = testGroupForMembersAndGroupReceiverBuilder.getGroupDto();
        this.groupsReceiverMessenger = testGroupForMembersAndGroupReceiverBuilder.getMessengerDto();
    }

    private UserCrudFacade initUserCrudFacade() {
        return new UserCrudFacade(userRepository);
    }

    private MemberCrudFacade initMemberCrudFacade() {
        return new MemberCrudFacade(memberRepository, userRepository, messengerRepository, groupCrudRepository);
    }

    private GroupCrudFacade initGroupCrudFacade() {
        return new GroupCrudFacade(groupCrudRepository, memberRepository, userRepository, messengerRepository);
    }

    private MessengerCrudFacade initMessengerCrudFacade() {
        return new MessengerCrudFacade(messengerRepository, userRepository, memberRepository, groupCrudRepository);
    }

    private MessageCrudFacade initMessageCrudFacade() {
        return new MessageCrudFacade(
                messageRepository,
                messengerRepository,
                groupCrudRepository,
                memberRepository,
                userRepository);
    }

    private boolean initTestDB() {
        userCrudFacade = initUserCrudFacade();
        memberCrudFacade = initMemberCrudFacade();
        groupCrudFacade = initGroupCrudFacade();
        messengerCrudFacade = initMessengerCrudFacade();

        UserEntity userEntityFirst = null;
        UserEntity userEntitySecond = null;
        try {
            userEntityFirst = userCrudFacade.findEntityByEmail(userCrudFacade.add(firstUser));
            userEntitySecond = userCrudFacade.findEntityByEmail(userCrudFacade.add(secondUser));
        } catch (UserNotFoundException | UserAlreadyExistsException e) {
            e.printStackTrace();
        }


        MemberEntity memberEntityFirst = null;
        try {
            memberEntityFirst = memberCrudFacade.findEntity(memberCrudFacade.add(memberSender));
        } catch (MemberNotFoundException | MemberAlreadyExistsException | UserNotFoundException | GroupNotFoundException | MessengerAlreadyExistsException | MessengerArgumentNotSpecified e) {
            e.printStackTrace();
        }

        MemberEntity memberEntitySecond = null;
        try {
            memberEntitySecond = memberCrudFacade.findEntity(memberCrudFacade.add(memberReceiver));
        } catch (MemberNotFoundException | MemberAlreadyExistsException | UserNotFoundException | GroupNotFoundException | MessengerAlreadyExistsException | MessengerArgumentNotSpecified e) {
            e.printStackTrace();
        }

        GroupEntity groupEntity = null;
        try {
            groupEntity = groupCrudFacade.findEntityByGroup(groupCrudFacade.add(testGroupAndReceiverGroup));
        } catch (GroupNotFoundException | GroupAlreadyExistsException | UserNotFoundException | MemberNotFoundException | MessengerAlreadyExistsException | MessengerArgumentNotSpecified e) {
            e.printStackTrace();
        }

        return userEntityFirst != null
                && userEntitySecond != null
                && memberEntityFirst != null
                && memberEntitySecond != null
                && groupEntity != null;
    }

    private List<MessageDto> saveMessagesInToDb(MessengerDto sender, MessengerDto receiver, String message, int numberOfTestMessages) throws UserNotFoundException, MessengerNotFoundException, MemberNotFoundException, GroupNotFoundException, MessageArgumentNotSpecifiedException {
        List<MessageDto> result = new ArrayList<>();
        MessageCrudFacade messageCrudFacade = initMessageCrudFacade();

        List<MessageDto> messagesToSave = new TestMessagesGenerator(message)
                .generateConversation(sender, receiver, numberOfTestMessages);

        for (MessageDto messageDto : messagesToSave) {
            result.add(messageCrudFacade.add(messageDto));
        }

        return result;
    }

    @Test
    public void INIT_TEST_DB() {
        boolean initDB = initTestDB();
        assertThat(initDB).isTrue();
    }

    @Test
    public void WHEN_create_new_messages_from_MEMBER_to_MEMBER_THEN_return_messages() throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException, MessageArgumentNotSpecifiedException {
        initTestDB();
        int numberOfMessagesLocal = 4;
        List<MessageDto> createdMessages = saveMessagesInToDb(membersSenderMessenger, membersReceiverMessenger, standardTestMessageText, numberOfMessagesLocal);

        assertThat(createdMessages.size()).isEqualTo(numberOfMessagesLocal);
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getSender().getMember()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getReceiver().getMember()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getMessage()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getCreationTime()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.isActive()).isTrue());
    }

    @Test
    public void WHEN_create_new_messages_from_MEMBER_to_GROUP_THEN_return_messages() throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException, MessageArgumentNotSpecifiedException {
        initTestDB();
        int numberOfMessagesLocal = 4;
        List<MessageDto> createdMessages = saveMessagesInToDb(membersSenderMessenger, groupsReceiverMessenger, standardTestMessageText, numberOfMessagesLocal);

        assertThat(createdMessages.size()).isEqualTo(numberOfMessagesLocal);
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getSender().getMember()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getReceiver().getGroup()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getMessage()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getCreationTime()).isNotNull());
        createdMessages
                .forEach(messageDto ->
                        assertThat(messageDto.isActive()).isTrue());
    }

    @Test
    public void WHEN_try_to_send_empty_message_THEN_throw_MessageArgumentNotSpecifiedException() {
        initTestDB();
        String emptyMessage = "";

        try {
            saveMessagesInToDb(membersSenderMessenger, membersReceiverMessenger, emptyMessage, 1);
            Assert.fail();

        } catch (GroupNotFoundException | UserNotFoundException | MessengerNotFoundException | MemberNotFoundException e) {
            Assert.fail();

        } catch (MessageArgumentNotSpecifiedException e) {
            assertThat(e)
                    .isInstanceOf(CrudException.class)
                    .hasMessage(MessageCrudStatusEnum.MESSAGE_IS_EMPTY.toString());
        }
    }

    @Test
    public void WHEN_found_messages_MEMBER_to_MEMBER_THEN_return_messages() throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException, MessageNotFoundException, MessageArgumentNotSpecifiedException {
        initTestDB();
        int numberOfMessagesLocal = 4;
        saveMessagesInToDb(membersSenderMessenger, membersReceiverMessenger, standardTestMessageText, numberOfMessagesLocal);

        MessageDto singleMessage = MessageDto.builder()
                .sender(membersSenderMessenger)
                .build();

        String senderMemberNick = membersSenderMessenger.getMember().getNick();
        List<MessageDto> foundMessages = initMessageCrudFacade().findAll(singleMessage);


        assertThat(foundMessages.size()).isEqualTo((numberOfMessagesLocal / 2));
        foundMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getSender().getMember().getNick().equals(senderMemberNick)).isTrue());
        foundMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getReceiver().getMember().getNick().equals(senderMemberNick)).isFalse());
    }

    @Test
    public void WHEN_found_messages_in_GROUP_THEN_return_messages() throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException, MessageNotFoundException, MessageArgumentNotSpecifiedException {
        initTestDB();
        int numberOfMessagesLocal = 4;
        saveMessagesInToDb(membersSenderMessenger, groupsReceiverMessenger, standardTestMessageText, numberOfMessagesLocal);

        MessageDto singleMessage = MessageDto.builder()
                .receiver(groupsReceiverMessenger)
                .build();

        String groupName = singleMessage.getReceiver().getGroup().getGroupName();
        List<MessageDto> foundMessages = initMessageCrudFacade().readAllGroupMessages(singleMessage);

        assertThat(foundMessages.size()).isEqualTo(numberOfMessagesLocal);
        foundMessages
                .forEach(messageDto ->
                        assertThat(messageDto.getReceiver().getGroup().getGroupName()).isEqualTo(groupName));
    }

    @Test
    public void WHEN_sender_is_not_specified_THEN_return_MessageArgumentNotSpecified() throws UserNotFoundException, MemberNotFoundException, MessageArgumentNotSpecifiedException, MessengerNotFoundException, GroupNotFoundException {
        initTestDB();
        MessageDto singleMessage = new MessageDto();
        saveMessagesInToDb(membersSenderMessenger, membersReceiverMessenger, standardTestMessageText, 1);

        try {
            initMessageCrudFacade().findAll(singleMessage);
            Assert.fail();

        } catch (UserNotFoundException | MessengerNotFoundException | MemberNotFoundException | GroupNotFoundException | MessageNotFoundException e) {
            Assert.fail();

        } catch (MessageArgumentNotSpecifiedException e) {
            assertThat(e)
                    .isInstanceOf(CrudException.class)
                    .hasMessage(MessageCrudStatusEnum.NOT_SPECIFIED_SENDER.toString());
        }
    }

    @Test
    public void WHEN_try_to_get_all_chat_group_messages_and_receiver_is_not_specified_THEN_return_MessageArgumentNotSpecified() throws UserNotFoundException, MemberNotFoundException, MessageArgumentNotSpecifiedException, MessengerNotFoundException, GroupNotFoundException {
        initTestDB();
        MessageDto singleMessage = new MessageDto();
        saveMessagesInToDb(membersSenderMessenger, groupsReceiverMessenger, standardTestMessageText, 1);

        try {
            initMessageCrudFacade().readAllGroupMessages(singleMessage);
            Assert.fail();

        } catch (UserNotFoundException | MessengerNotFoundException | MemberNotFoundException | GroupNotFoundException | MessageNotFoundException e) {
            Assert.fail();

        } catch (MessageArgumentNotSpecifiedException e) {
            assertThat(e)
                    .isInstanceOf(CrudException.class)
                    .hasMessage(MessageCrudStatusEnum.NOT_SPECIFIED_RECEIVER.toString());
        }
    }

    @Test
    public void WHEN_update_message_THEN_return_updated_message() throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException, MessageArgumentNotSpecifiedException, MessageNotFoundException {
        initTestDB();
        int numberOfMessagesLocal = 1;
        List<MessageDto> savedMessages = saveMessagesInToDb(membersSenderMessenger, groupsReceiverMessenger, standardTestMessageText, numberOfMessagesLocal);

        String newMessageText = "UPDATED TEXT";
        MessageDto oldVersionMessage = savedMessages.get(0);
        MessageDto newVersionMessage = MessageDto.builder()
                .message(newMessageText)
                .build();

        MessageDto updated = initMessageCrudFacade().update(oldVersionMessage, newVersionMessage);

        assertThat(updated.getSender()).isNotNull();
        assertThat(updated.getReceiver()).isNotNull();
        assertThat(updated.getMessage()).isEqualTo(newMessageText);
    }

    @Test
    public void WHEN_try_to_update_empty_message_text_THEN_return_MessageArgumentNotSpecifiedException() throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException, MessageArgumentNotSpecifiedException {
        initTestDB();
        int numberOfMessagesLocal = 1;
        List<MessageDto> savedMessages = saveMessagesInToDb(membersSenderMessenger, groupsReceiverMessenger, standardTestMessageText, numberOfMessagesLocal);

        String newMessageText = "";
        MessageDto oldVersionMessage = savedMessages.get(0);
        MessageDto newVersionMessage = MessageDto.builder()
                .message(newMessageText)
                .build();

        try {
            initMessageCrudFacade().update(oldVersionMessage, newVersionMessage);
            Assert.fail();

        } catch (MessageNotFoundException e) {
            Assert.fail();

        } catch (MessageArgumentNotSpecifiedException e) {
            assertThat(e)
                    .isInstanceOf(CrudException.class)
                    .hasMessage(MessageCrudStatusEnum.MESSAGE_IS_EMPTY.toString());
        }
    }

    @Test
    public void WHEN_delete_existing_message_THEN_return_deleted_message() throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException, MessageArgumentNotSpecifiedException, MessageNotFoundException, MessageFoundButNotActiveException {
        initTestDB();
        int numberOfMessagesLocal = 3;
        List<MessageDto> savedMessages = saveMessagesInToDb(membersSenderMessenger, groupsReceiverMessenger, standardTestMessageText, numberOfMessagesLocal);

        MessageDto messageToDelete = savedMessages.get(0);
        MessageDto deleted = initMessageCrudFacade().delete(messageToDelete);

        assertThat(deleted.getSender()).isNotNull();
        assertThat(deleted.getReceiver()).isNotNull();
        assertThat(deleted.getCreationTime()).isNotNull();
        assertThat(deleted.isActive()).isFalse();
        assertThat(deleted.getModificationTime()).isNotNull();
    }

    @Test
    public void WHEN_try_to_delete_deleted_message_THEN_return_MessageFoundButNotActiveException() throws UserNotFoundException, MemberNotFoundException, MessengerNotFoundException, GroupNotFoundException, MessageArgumentNotSpecifiedException, MessageNotFoundException, MessageFoundButNotActiveException {
        initTestDB();
        int numberOfMessagesLocal = 3;
        List<MessageDto> savedMessages = saveMessagesInToDb(membersSenderMessenger, groupsReceiverMessenger, standardTestMessageText, numberOfMessagesLocal);

        MessageDto messageToDelete = savedMessages.get(0);
        initMessageCrudFacade().delete(messageToDelete);

        try {
            initMessageCrudFacade().delete(messageToDelete);
            Assert.fail();

        } catch (MessageFoundButNotActiveException e) {
            assertThat(e)
                    .isInstanceOf(CrudException.class)
                    .hasMessage(MessageCrudStatusEnum.MESSAGE_FOUND_BUT_NOT_ACTIVE.toString());
        }
    }
}