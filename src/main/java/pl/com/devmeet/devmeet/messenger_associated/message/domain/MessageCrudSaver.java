package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class MessageCrudSaver {

    private MessageRepository messageRepository;

    public MessageEntity saveEntity(MessageEntity messageEntity) {
        return  messageRepository.save(messageEntity);
    }
}