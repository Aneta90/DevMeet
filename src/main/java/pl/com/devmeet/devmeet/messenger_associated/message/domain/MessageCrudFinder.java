package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import java.util.ArrayList;
import java.util.List;

public class MessageCrudFinder {

    private MessageRepository repository;

    public MessageCrudFinder(MessageRepository repository) {
        this.repository = repository;
    }

    public List<MessageEntity> findEntityByFromMember(String memberNick) {
        List<MessageEntity> foundMember = new ArrayList<>();

        if (memberNick != null && !memberNick.equals("")) {
//            foundMember = repository.findMessagesFromMember(memberNick);
        }
        return foundMember;
    }

    public List<MessageEntity> findEntityByToMember(String memberNick) {
        List<MessageEntity> foundMember = new ArrayList<>();

        if (memberNick != null && !memberNick.equals("")) {
//            foundMember = repository.findMessagesToMember(memberNick);
        }
        return foundMember;
    }
}