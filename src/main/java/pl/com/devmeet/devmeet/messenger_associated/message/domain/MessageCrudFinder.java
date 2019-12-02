package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.message.domain.status_and_exceptions.MessageCrudStatusEnum;
import pl.com.devmeet.devmeet.messenger_associated.message.domain.status_and_exceptions.MessageNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerEntity;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
class MessageCrudFinder implements CrudEntityFinder<MessageDto, MessageEntity> {

    private MessageRepository repository;
    private MessengerFinder messengerFinder;

    @Override
    public MessageEntity findEntity(MessageDto dto) throws UserNotFoundException, GroupNotFoundException, MessengerNotFoundException, MemberNotFoundException, MessageNotFoundException {
        MessengerEntity sender = findSender(dto);

        Optional<MessageEntity> found = repository.findBySenderAndMessage(sender, dto.getMessage());

        if (found.isPresent())
            return found.get();

        throw new MessageNotFoundException(MessageCrudStatusEnum.MESSAGE_NOT_FOUND_BY_SENDER_AND_MESSAGE.toString());
    }


    @Override
    public List<MessageEntity> findEntities(MessageDto dto) throws CrudException {
        return null;
    }

    private MessengerEntity findSender(MessageDto dto) throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException {
        MessengerDto sender;
        try {
            sender = dto.getSender();
            return messengerFinder.findMessenger(sender);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private MessengerEntity findReceiver(MessageDto dto) throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException {
        MessengerDto receiver;
        try {
            receiver = dto.getSender();
            return messengerFinder.findMessenger(receiver);
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public boolean isExist(MessageDto dto) {
        return false;
    }
}