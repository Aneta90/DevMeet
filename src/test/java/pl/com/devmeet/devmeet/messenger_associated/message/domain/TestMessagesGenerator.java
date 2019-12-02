package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import pl.com.devmeet.devmeet.member_associated.member.domain.MemberDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 02.12.2019
 * Time: 18:09
 */
class TestMessagesGenerator {

    private int index = -1;

    public List<MessageDto> generate(MessengerDto sender, MessengerDto receiver, int numberOfMessages) {
        String senderNickname = sender.getMember().getNick();
        String receiverNickname = receiver.getMember().getNick();

        return testMessagesGenerator(senderNickname, receiverNickname, numberOfMessages).stream()
                .map(message ->
                        messageBuilderWithSwitcher(sender, receiver, message, index++)
                )
                .collect(Collectors.toList());
    }

    private MessageDto messageBuilderWithSwitcher(MessengerDto sender, MessengerDto receiver, String message, int numberOfMessages) {
        return numberOfMessages % 2 == 0 ?
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

    private List<String> testMessagesGenerator(String senderNickname, String receiverNickname, int numberOfMessages) {
        List<String> result = new ArrayList<>();
        int index = 0;

        while (index < numberOfMessages) {
            result.add(senderNickname + " to " + receiverNickname + " test message number: " + (index + 1));
            index++;
        }

        return result;
    }
}
