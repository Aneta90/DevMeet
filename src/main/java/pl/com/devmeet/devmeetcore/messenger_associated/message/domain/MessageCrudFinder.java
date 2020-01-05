package pl.com.devmeet.devmeetcore.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageArgumentNotSpecifiedException;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageCrudStatusEnum;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerEntity;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class MessageCrudFinder implements CrudEntityFinder<MessageDto, MessageEntity> {

    private MessageRepository repository;
    private MessengerFinder messengerFinder;

    @Override
    public MessageEntity findEntity(MessageDto dto) throws UserNotFoundException, GroupNotFoundException, MessengerNotFoundException, MemberNotFoundException, MessageNotFoundException, MessageArgumentNotSpecifiedException {
        MessengerEntity sender = findSender(dto);

        Optional<MessageEntity> found = repository.findBySenderAndMessage(sender, dto.getMessage());

        if (found.isPresent())
            return found.get();

        throw new MessageNotFoundException(MessageCrudStatusEnum.MESSAGE_NOT_FOUND_BY_SENDER_AND_MESSAGE.toString());
    }


    @Override
    public List<MessageEntity> findEntities(MessageDto dto) throws UserNotFoundException, GroupNotFoundException, MessengerNotFoundException, MemberNotFoundException, MessageNotFoundException, MessageArgumentNotSpecifiedException {
        MessengerEntity sender = findSender(dto);

        Optional<List<MessageEntity>> allMessagesBySender = repository.findAllBySender(sender);

        if (allMessagesBySender.isPresent())
            return allMessagesBySender.get();

        throw new MessageNotFoundException(MessageCrudStatusEnum.MESSAGES_NOT_FOUND_BY_MEMBER.toString());
    }

    public List<MessageEntity> findEntitiesByGroupForGroupChat(MessageDto dto) throws MessageNotFoundException, UserNotFoundException, GroupNotFoundException, MessengerNotFoundException, MemberNotFoundException, MessageArgumentNotSpecifiedException {
        MessengerEntity receiver = findReceiver(dto);

        Optional<List<MessageEntity>> allGroupChatMessages = repository.findAllByReceiver(receiver);

        if (allGroupChatMessages.isPresent())
            return allGroupChatMessages.get();

        throw new MessageNotFoundException(MessageCrudStatusEnum.MESSAGES_NOT_FOUND_BY_GROUP.toString());
    }

    private MessengerEntity findSender(MessageDto dto) throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException, MessageArgumentNotSpecifiedException {
        MessengerDto sender = null;
        try {
            sender = dto.getSender();
        } catch (NullPointerException e) {
        }
        if (sender != null)
            return messengerFinder.findMessenger(sender);

        throw new MessageArgumentNotSpecifiedException(MessageCrudStatusEnum.NOT_SPECIFIED_SENDER.toString());
    }

    private MessengerEntity findReceiver(MessageDto dto) throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException, MessageArgumentNotSpecifiedException {
        MessengerDto receiver = null;
        try {
            receiver = dto.getReceiver();
        } catch (NullPointerException e) {
        }

        if (receiver != null)
            return messengerFinder.findMessenger(receiver);

        throw new MessageArgumentNotSpecifiedException(MessageCrudStatusEnum.NOT_SPECIFIED_RECEIVER.toString());
    }

    @Override
    public boolean isExist(MessageDto dto) {
        try {
            return findEntity(dto) != null;
        } catch (UserNotFoundException | GroupNotFoundException | MessengerNotFoundException | MemberNotFoundException | MessageNotFoundException | MessageArgumentNotSpecifiedException e) {
            return false;
        }
    }
}