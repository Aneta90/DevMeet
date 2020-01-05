package pl.com.devmeet.devmeetcore.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntitySaver;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class MessageCrudSaver implements CrudEntitySaver<MessageEntity, MessageEntity> {

    private MessageRepository messageRepository;

    @Override
    public MessageEntity saveEntity(MessageEntity messageEntity) {
        return  messageRepository.save(messageEntity);
    }
}