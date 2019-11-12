package pl.com.devmeet.devmeet.messenger_associated.message.domain;

public class MessageCrudCreator {

    private MessageCrudSaver messageCrudSaver;
    private MessageCrudFinder messageCrudFinder;

    public MessageCrudCreator(MessageRepository messageRepository) {
        this.messageCrudSaver = new MessageCrudSaver(messageRepository);
        this.messageCrudFinder = new MessageCrudFinder(messageRepository);
    }

    public MessageEntity create (MessageDto messageDto){ //tu nie sprawdzamy, czy dana wiadomość istnieje, bo można utworzyć wiele takich samych wiadomości
            return messageCrudSaver.saveEntity(MessageMapper.toEntity(messageDto));
    }
}