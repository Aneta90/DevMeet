package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;

class MessengerMapper {

    static MessengerDto map(MessengerEntity messengerEntity) {
        return messengerEntity != null ? MessengerDto.builder()
                .member(MemberCrudFacade.map(messengerEntity.getMember()))
                .group(GroupCrudFacade.map(messengerEntity.getGroup()))
                .creationTime(messengerEntity.getCreationTime())
                .isActive(messengerEntity.isActive())
                .build() : null;

    }

    static MessengerEntity map(MessengerDto messengerDto) {
        return messengerDto != null ? MessengerEntity.builder()
                .member(MemberCrudFacade.map(messengerDto.getMember()))
                .group(GroupCrudFacade.map(messengerDto.getGroup()))
                .creationTime(messengerDto.getCreationTime())
                .isActive(messengerDto.isActive())
                .build() : null;
    }
}
