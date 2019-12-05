package pl.com.devmeet.devmeet.messenger_associated.message.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.message.status_and_exceptions.MessageArgumentNotSpecifiedException;
import pl.com.devmeet.devmeet.messenger_associated.message.status_and_exceptions.MessageCrudStatusEnum;
import pl.com.devmeet.devmeet.messenger_associated.message.status_and_exceptions.MessageNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class MessageCrudUpdater implements CrudEntityUpdater<MessageDto, MessageEntity> {

    private MessageCrudFinder messageCrudFinder;
    private MessageCrudSaver messageCrudSaver;

    @Override
    public MessageEntity updateEntity(MessageDto oldDto, MessageDto newDto) throws MessageNotFoundException, GroupNotFoundException, MessengerNotFoundException, UserNotFoundException, MessageArgumentNotSpecifiedException, MemberNotFoundException {
        MessageEntity foundMessage = messageCrudFinder.findEntity(oldDto);

        return messageCrudSaver.saveEntity(
                updateAllowedParameters(foundMessage, messageChecker(newDto)));
    }

    private MessageEntity updateAllowedParameters(MessageEntity oldEntity, MessageDto newDto) {
        oldEntity.setMessage(newDto.getMessage());
        oldEntity.setModificationTime(DateTime.now());

        return oldEntity;
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