package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerCrudFacade;

class MessageMapper {

    static MessageDto toDto(MessageEntity messageEntity) {

        return messageEntity != null ? MessageDto.builder()
                .creationTime(messageEntity.getCreationTime())
                .sender(MessengerCrudFacade.map(messageEntity.getSender()))
                .receiver(MessengerCrudFacade.map(messageEntity.getReceiver()))
                .message(messageEntity.getMessage())
                .build() : null;
    }

    static MessageEntity toEntity(MessageDto messageDto) {
        return messageDto != null ? MessageEntity.builder()
                .creationTime(messageDto.getCreationTime())
                .sender(MessengerCrudFacade.map(messageDto.getSender()))
                .receiver(MessengerCrudFacade.map(messageDto.getReceiver()))
                .message(messageDto.getMessage())
                .build() : null;
    }
}
