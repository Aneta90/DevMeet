package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerCrudFacade;

class MessageMapper {

    static MessageDto toDto(MessageEntity messageEntity) {

        return messageEntity != null ? MessageDto.builder()
                .sender(MessengerCrudFacade.map(messageEntity.getSender()))
                .receiver(MessengerCrudFacade.map(messageEntity.getReceiver()))
                .message(messageEntity.getMessage())
                .modificationTime(messageEntity.getModificationTime())
                .creationTime(messageEntity.getCreationTime())
                .isActive(messageEntity.isActive())
                .build() : null;
    }

    static MessageEntity toEntity(MessageDto messageDto) {
        return messageDto != null ? MessageEntity.builder()
                .sender(MessengerCrudFacade.map(messageDto.getSender()))
                .receiver(MessengerCrudFacade.map(messageDto.getReceiver()))
                .message(messageDto.getMessage())
                .modificationTime(messageDto.getModificationTime())
                .creationTime(messageDto.getCreationTime())
                .isActive(messageDto.isActive())
                .build() : null;
    }
}
