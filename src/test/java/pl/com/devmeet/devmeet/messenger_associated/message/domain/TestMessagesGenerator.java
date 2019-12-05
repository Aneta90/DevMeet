package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 02.12.2019
 * Time: 18:09
 */
@RequiredArgsConstructor
class TestMessagesGenerator {

    @NonNull
    private String testMessage;

    private int index = -1;

    public List<MessageDto> generateConversation(MessengerDto sender, MessengerDto receiver, int numberOfMessages) {
        boolean memberReceiver = checkIsMemberReceiver(receiver);
        boolean groupReceiver = checkIsGroupReceiver(receiver);

        if (memberReceiver && !groupReceiver)
            return generateMemberToMemberConversation(sender, receiver, numberOfMessages);

        else if (groupReceiver && !memberReceiver)
            return generateMemberToGroupConversation(sender, receiver, numberOfMessages);

        else
            return null;
    }

    private boolean checkIsMemberReceiver(MessengerDto receiver) {
        try {
            return receiver.getMember() != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkIsGroupReceiver(MessengerDto receiver) {
        try {
            return receiver.getGroup() != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private List<MessageDto> generateMemberToMemberConversation(MessengerDto sender, MessengerDto receiver, int numberOfMessages) {
        return testMessagesGenerator(sender.getMember().getNick(), receiver.getMember().getNick(), numberOfMessages).stream()
                .map(message ->
                        messageBuilderWithSwitching(sender, receiver, message, index++)
                )
                .collect(Collectors.toList());
    }

    private MessageDto messageBuilderWithSwitching(MessengerDto sender, MessengerDto receiver, String message, int objectIndex) {
        return objectIndex % 2 == 0 ?
                MessageDto.builder()
                        .sender(receiver)
                        .receiver(sender)
                        .message(message)
                        .build()
                :
                MessageDto.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .message(message)
                        .build();
    }

    private List<MessageDto> generateMemberToGroupConversation(MessengerDto sender, MessengerDto receiver, int numberOfMessages) {
        return testMessagesGenerator(sender.getMember().getNick(), receiver.getGroup().getGroupName(), numberOfMessages).stream()
                .map(message ->
                        simpleMessageBuilder(sender, receiver, message)
                )
                .collect(Collectors.toList());
    }

    private MessageDto simpleMessageBuilder(MessengerDto sender, MessengerDto receiver, String message) {
        return MessageDto.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .build();
    }

    private List<String> testMessagesGenerator(String senderNickname, String receiverNickname, int numberOfMessages) {
        List<String> result = new ArrayList<>();
        int index = 0;

        while (index < numberOfMessages) {
            if (testMessage != null && !testMessage.equals(""))
                result.add(generateStringMessage(senderNickname, receiverNickname, index));
            else
                result.add("");

            index++;
        }

        return result;
    }

    private String generateStringMessage(String senderNickname, String receiverNickname, int messageIndex) {
        return String
                .format("'%s' to '%s' test message number: %d \nMessage: %s", senderNickname, receiverNickname, messageIndex, testMessage);
    }
}
