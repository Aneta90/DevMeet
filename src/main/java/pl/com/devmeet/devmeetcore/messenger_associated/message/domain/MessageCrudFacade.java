package pl.com.devmeet.devmeetcore.messenger_associated.message.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeetcore.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageArgumentNotSpecifiedException;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageFoundButNotActiveException;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.UserRepository;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageCrudFacade implements CrudFacadeInterface<MessageDto, MessageEntity> {

    private MessageRepository messageRepository;

    private MessengerRepository messengerRepository;
    private GroupCrudRepository groupCrudRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;

    @Autowired
    public MessageCrudFacade(MessageRepository messageRepository, MessengerRepository messengerRepository, GroupCrudRepository groupCrudRepository, MemberRepository memberRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.messengerRepository = messengerRepository;
        this.groupCrudRepository = groupCrudRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    private MessengerFinder initMessengerFinder() {
        return MessengerFinder.builder()
                .messengerRepository(messengerRepository)
                .userRepository(userRepository)
                .memberRepository(memberRepository)
                .groupCrudRepository(groupCrudRepository)
                .build();
    }

    private MessageCrudFinder initFinder() {
        return MessageCrudFinder.builder()
                .repository(messageRepository)
                .messengerFinder(initMessengerFinder())
                .build();
    }

    private MessageCrudCreator initCreator() {
        return MessageCrudCreator.builder()
                .messengerFinder(initMessengerFinder())
                .messageCrudSaver(initSaver())
                .messageCrudFinder(initFinder())
                .build();
    }

    private MessageCrudSaver initSaver() {
        return MessageCrudSaver.builder()
                .messageRepository(messageRepository)
                .build();
    }

    private MessageCrudUpdater initUpdater() {
        return MessageCrudUpdater.builder()
                .messageCrudSaver(initSaver())
                .messageCrudFinder(initFinder())
                .build();
    }

    private MessageCrudDeactivator initDeleter() {
        return MessageCrudDeactivator.builder()
                .messageCrudSaver(initSaver())
                .messageCrudFinder(initFinder())
                .build();
    }

    @Override
    public MessageDto add(MessageDto dto) throws UserNotFoundException, GroupNotFoundException, MessengerNotFoundException, MemberNotFoundException, MessageArgumentNotSpecifiedException {
        return map(initCreator().createEntity(dto));
    }

    public MessageDto find(MessageDto dto) throws UserNotFoundException, MessengerNotFoundException, MemberNotFoundException, GroupNotFoundException, MessageNotFoundException, MessageArgumentNotSpecifiedException {
        return map(findEntity(dto));
    }

    public List<MessageDto> findAll(MessageDto dto) throws UserNotFoundException, MessengerNotFoundException, MemberNotFoundException, GroupNotFoundException, MessageNotFoundException, MessageArgumentNotSpecifiedException {
        return mapToDtos(findEntities(dto));
    }

    public List<MessageDto> readAllGroupMessages(MessageDto dto) throws UserNotFoundException, MessengerNotFoundException, MemberNotFoundException, GroupNotFoundException, MessageNotFoundException, MessageArgumentNotSpecifiedException {
        return mapToDtos(initFinder().findEntitiesByGroupForGroupChat(dto));
    }

    @Override
    public MessageDto update(MessageDto oldDto, MessageDto newDto) throws MessageNotFoundException, GroupNotFoundException, MessengerNotFoundException, UserNotFoundException, MessageArgumentNotSpecifiedException, MemberNotFoundException {
        return map(initUpdater().updateEntity(oldDto, newDto));
    }

    @Override
    public MessageDto delete(MessageDto dto) throws MessageNotFoundException, GroupNotFoundException, MessengerNotFoundException, UserNotFoundException, MessageArgumentNotSpecifiedException, MemberNotFoundException, MessageFoundButNotActiveException {
        return map(initDeleter().deleteEntity(dto));
    }

    public MessageEntity findEntity(MessageDto dto) throws MessageNotFoundException, GroupNotFoundException, MessengerNotFoundException, UserNotFoundException, MessageArgumentNotSpecifiedException, MemberNotFoundException {
        return initFinder().findEntity(dto);
    }

    public List<MessageEntity> findEntities(MessageDto dto) throws MessageNotFoundException, GroupNotFoundException, MessengerNotFoundException, UserNotFoundException, MessageArgumentNotSpecifiedException, MemberNotFoundException {
        return initFinder().findEntities(dto);
    }

    public static MessageDto map(MessageEntity entity) {
        return MessageMapper.toDto(entity);
    }

    public static MessageEntity map(MessageDto dto) {
        return MessageMapper.toEntity(dto);
    }

    public static List<MessageDto> mapToDtos(List<MessageEntity> entities) {
        return entities.stream()
                .map(MessageCrudFacade::map)
                .collect(Collectors.toList());
    }
}