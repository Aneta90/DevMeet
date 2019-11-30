package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudRepository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerDto;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerRepository;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 27.11.2019
 * Time: 23:05
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
class TestGroupAndGroupReceiverInitiator {

    private GroupCrudRepository groupRepository;
    private MessengerRepository messengerRepository;

    private GroupDto groupDto;
    private MessengerDto messengerDto;

    public void init(){
        initGroup();
        initMessenger();
    }

    private void initGroup(){
        this.groupDto = GroupDto.builder()
                .groupName("Java test group")
                .website("www.testWebsite.com")
                .description("Welcome to test group")
                .messenger(null)
                .membersLimit(5)
                .memberCounter(6)
                .meetingCounter(1)
                .creationTime(null)
                .modificationTime(null)
                .isActive(false)
                .build();
    }

    private void initMessenger(){
        this.messengerDto = MessengerDto.builder()
                .group(groupDto)
                .build();
    }
}
