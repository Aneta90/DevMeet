package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;

class MessengerMapper {

    static MessengerDto map(MessengerEntity messengerEntity) {

        return new MessengerDto().builder()
                .messengerName(messengerEntity.getMessengerName())
                .member(MemberCrudFacade.map(messengerEntity.getMember()))
                //.messages(messengerEntity.getMessages())
                .isActive(messengerEntity.isActive())
                .group(GroupCrudFacade.map(messengerEntity.getGroup()))
                .creationTime(messengerEntity.getCreationTime())
                .build();

    }

    static MessengerEntity map(MessengerDto messengerDto) {

        return new MessengerEntity().builder()
                .messengerName(messengerDto.getMessengerName())
                // .messages(messengerDto.getMessages())
                .group(GroupCrudFacade.map(messengerDto.getGroup()))
                .creationTime(messengerDto.getCreationTime())
                .member(MemberCrudFacade.map(messengerDto.getMember()))
                .isActive(messengerDto.isActive())
                .build();
    }
}
