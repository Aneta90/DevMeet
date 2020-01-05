package pl.com.devmeet.devmeetcore.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageArgumentNotSpecifiedException;
import pl.com.devmeet.devmeetcore.messenger_associated.message.status_and_exceptions.MessageCrudStatusEnum;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerEntity;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class MessageCrudCreator implements CrudEntityCreator<MessageDto, MessageEntity> {

    private MessageCrudSaver messageCrudSaver;
    private MessageCrudFinder messageCrudFinder;
    private MessengerFinder messengerFinder;

    @Override
    public MessageEntity createEntity(MessageDto dto) throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException, MessageArgumentNotSpecifiedException {
        MessageWithMessengersConnector messengersConnector = new MessageWithMessengersConnector();

        MessengerEntity sender = messengerFinder.findMessenger(dto.getSender());
        MessengerEntity receiver = messengerFinder.findMessenger(dto.getReceiver());

        dto = messageChecker(dto);

        return messageCrudSaver.saveEntity(
                setDefaultValuesWhenMessageNotExists(
                        messengersConnector
                                .connectMessengers(MessageCrudFacade.map(dto), sender, receiver)));

    }

    private MessageEntity setDefaultValuesWhenMessageNotExists(MessageEntity messageEntity) {
        messageEntity.setCreationTime(DateTime.now());
        messageEntity.setActive(true);

        return messageEntity;
    }

    private MessageDto messageChecker(MessageDto messageDto) throws MessageArgumentNotSpecifiedException {
        String message;

        try {
            message = messageDto.getMessage();
            checkIsMessageIsNotEmpty(message);

        } catch (NullPointerException e) {
            throw new MessageArgumentNotSpecifiedException(MessageCrudStatusEnum.MESSAGE_IS_EMPTY.toString());
        }

        return messageDto;
    }

    private void checkIsMessageIsNotEmpty(String message) throws MessageArgumentNotSpecifiedException {
        if (message.equals(""))
            throw new MessageArgumentNotSpecifiedException(MessageCrudStatusEnum.MESSAGE_IS_EMPTY.toString());
    }
}