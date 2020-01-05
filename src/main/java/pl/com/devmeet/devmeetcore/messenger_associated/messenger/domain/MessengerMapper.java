package pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;

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
