package pl.com.devmeet.devmeet.messenger_associated.message.domain;

class MessageCrudUpdater {

    private MessageRepository messageRepository;
    private MessageCrudSaver messageCrudSaver;
    private MessageCrudFinder messageCrudFinder;

    public MessageCrudUpdater(MessageRepository repository) {
        this.messageCrudSaver = new MessageCrudSaver(repository);
        this.messageCrudFinder = new MessageCrudFinder(repository);
    }

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