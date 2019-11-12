package pl.com.devmeet.devmeet.messenger_associated.message.domain;

public interface MessageCrudInterface {

    MessageDto create(MessageDto messageDto);

    MessageDto delete(MessageDto messageDto);

    MessageDto edit(MessageDto messageDto);

    static MessageDto map(MessageEntity entity) {
        return MessageMapper.toDto(entity);
    }

    static MessageEntity map(MessageDto dto) {
        return MessageMapper.toEntity(dto);
    }
}
