package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.message.status_and_exceptions.MessageArgumentNotSpecifiedException;
import pl.com.devmeet.devmeet.messenger_associated.message.status_and_exceptions.MessageCrudStatusEnum;
import pl.com.devmeet.devmeet.messenger_associated.message.status_and_exceptions.MessageFoundButNotActiveException;
import pl.com.devmeet.devmeet.messenger_associated.message.status_and_exceptions.MessageNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class MessageCrudDeleter implements CrudEntityDeleter<MessageDto, MessageEntity> {

    private MessageCrudFinder messageCrudFinder;
    private MessageCrudSaver messageCrudSaver;


    @Override
    public MessageEntity deleteEntity(MessageDto dto) throws MessageNotFoundException, GroupNotFoundException, MessengerNotFoundException, UserNotFoundException, MessageArgumentNotSpecifiedException, MemberNotFoundException, MessageFoundButNotActiveException {
        MessageEntity foundMessage = messageCrudFinder.findEntity(dto);

        if (foundMessage.isActive())
            return messageCrudSaver.saveEntity(
                    setDefaultValuesOnDelete(foundMessage));

        throw new MessageFoundButNotActiveException(MessageCrudStatusEnum.MESSAGE_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private MessageEntity setDefaultValuesOnDelete(MessageEntity messageEntity) {
        messageEntity.setActive(false);
        messageEntity.setModificationTime(DateTime.now());

        return messageEntity;
    }
}