package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import pl.com.devmeet.devmeet.member_associated.member.domain.MemberCrudFacade;

public class MessageMapper {

    static MessageDto toDto(MessageEntity messageEntity) {

        return new MessageDto().builder()
                .creationTime(messageEntity.getCreationTime())
                .toMember(MemberCrudFacade.map(messageEntity.getToMember()))
                .fromMember(MemberCrudFacade.map(messageEntity.getFromMember()))
                .message(messageEntity.getMessage())
                .build();
    }

    static MessageEntity toEntity(MessageDto messageDto) {
        return new MessageEntity().builder()
                .creationTime(messageDto.getCreationTime())
                .toMember(MemberCrudFacade.map(messageDto.getToMember()))
                .fromMember(MemberCrudFacade.map(messageDto.getToMember()))
                .message(messageDto.getMessage())
                .build();
    }
}
