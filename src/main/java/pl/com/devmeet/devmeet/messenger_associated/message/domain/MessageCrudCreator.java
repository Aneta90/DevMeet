package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerEntity;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class MessageCrudCreator implements CrudEntityCreator<MessageDto, MessageEntity> {

    private MessageCrudSaver messageCrudSaver;
    private MessageCrudFinder messageCrudFinder;
    private MessengerFinder messengerFinder;

    @Override
    public MessageEntity createEntity(MessageDto dto) throws UserNotFoundException, MessengerNotFoundException, GroupNotFoundException, MemberNotFoundException {
        MessageWithMessengersConnector messengersConnector = new MessageWithMessengersConnector();
        MessageEntity messageEntity = null;

        MessengerEntity sender = messengerFinder.findMessenger(dto.getSender());
        MessengerEntity receiver = messengerFinder.findMessenger(dto.getReceiver());

        try {
            messageCrudFinder.findEntity(dto);

        } catch (CrudException e) {
            return messageCrudSaver.saveEntity(
                    setDefaultValuesWhenMessageNotExists(
                            messengersConnector
                                    .connectMessengers(MessageCrudFacade.map(dto), sender, receiver)));
        }

        return null;
    }

    private MessageEntity setDefaultValuesWhenMessageNotExists(MessageEntity messageEntity) {
        messageEntity.setCreationTime(DateTime.now());
        messageEntity.setActive(true);

        return messageEntity;
    }
}