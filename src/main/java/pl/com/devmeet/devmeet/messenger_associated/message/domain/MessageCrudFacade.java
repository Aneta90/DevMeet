package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeet.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberRepository;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerRepository;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.UserRepository;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

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
        return new MessageCrudFinder(messageRepository, initMessengerFinder());
    }

    private MessageCrudCreator initCreator() {
        return MessageCrudCreator.builder()
                .messengerFinder(initMessengerFinder())
                .messageCrudSaver(initSaver())
                .messageCrudFinder(initFinder())
                .build();
    }

    private MessageCrudSaver initSaver() {
        return new MessageCrudSaver(messageRepository);
    }

    private MessageCrudUpdater initUpdater() {
        return new MessageCrudUpdater(initFinder(), initSaver());
    }

    private MessageCrudDeleter initDeleter() {
        return new MessageCrudDeleter(initFinder(), initSaver());
    }

    @Override
    public MessageDto create(MessageDto dto) throws UserNotFoundException, GroupNotFoundException, MessengerNotFoundException, MemberNotFoundException {
        return map(initCreator().createEntity(dto));
    }

    @Override
    public MessageDto read(MessageDto dto) throws CrudException {
        return null;
    }

    @Override
    public List<MessageDto> readAll(MessageDto dto) throws CrudException {
        return null;
    }

    @Override
    public MessageDto update(MessageDto oldDto, MessageDto newDto) throws CrudException {
        return null;
    }

    @Override
    public MessageDto delete(MessageDto dto) throws CrudException {
        return null;
    }

    @Override
    public MessageEntity findEntity(MessageDto dto) throws CrudException {
        return null;
    }

    @Override
    public List<MessageEntity> findEntities(MessageDto dto) throws CrudException {
        return null;
    }

    public static MessageDto map(MessageEntity entity) {
        return MessageMapper.toDto(entity);
    }

    public static MessageEntity map(MessageDto dto) {
        return MessageMapper.toEntity(dto);
    }
}