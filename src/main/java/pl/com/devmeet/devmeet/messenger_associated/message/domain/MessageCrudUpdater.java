package pl.com.devmeet.devmeet.messenger_associated.message.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class MessageCrudUpdater {

    private MessageCrudFinder messageCrudFinder;
    private MessageCrudSaver messageCrudSaver;

    public MessageEntity updateEntity(MessageDto oldDto, MessageDto newDto) {

        MessageEntity oldMessageEntity = mapDtoToEntity(oldDto);
        MessageEntity newMessageEntity = mapDtoToEntity(newDto);

        return messageCrudSaver.saveEntity(updateAllowedParameters(oldMessageEntity, newMessageEntity));
    }

    private MessageEntity mapDtoToEntity(MessageDto dto) {
        return MessageCrudFacade.map(dto);
    }

    private MessageEntity updateAllowedParameters(MessageEntity oldEntity, MessageEntity newEntity) {
        oldEntity.setMessage(newEntity.getMessage());
        return oldEntity;
    }
}