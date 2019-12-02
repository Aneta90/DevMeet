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

    public List<MessageDto> generate(MessengerDto sender, MessengerDto receiver, int numberOfMessages) {
        String senderNickname = sender.getMember().getNick();
        String receiverNickname = receiver.getMember().getNick();

        return testMessagesGenerator(senderNickname, receiverNickname, numberOfMessages).stream()
                .map(message -> MessageDto.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .message(message)
                        .build())
                .collect(Collectors.toList());
    }

    private List<String> testMessagesGenerator(String senderNickname, String receiverNickname, int numberOfMessages) {
        List<String> result = new ArrayList<>();
        int index = 0;

        while (index < numberOfMessages) {
            result.add(senderNickname + " to " + receiverNickname + " test message number: " + index);
            index++;
        }

        return result;
    }
}
