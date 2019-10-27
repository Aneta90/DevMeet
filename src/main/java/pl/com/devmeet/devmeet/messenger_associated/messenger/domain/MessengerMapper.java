package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

class MessengerMapper {

    static MessengerDto map(MessengerEntity messengerEntity){

        return new MessengerDto().builder()
                //.member(messengerEntity.getMember())
                //.messages(messengerEntity.getMessages())
                .isActive(messengerEntity.isActive())
                //.group(messengerEntity.getGroup())
                .creationTime(messengerEntity.getCreationTime())
                .build();

    }

    static MessengerEntity map(MessengerDto messengerDto){

        return new MessengerEntity().builder()
               // .messages(messengerDto.getMessages())
               // .group(messengerDto.getGroup())
                .creationTime(messengerDto.getCreationTime())
               // .member(messengerDto.getMember())
                .isActive(messengerDto.isActive())
                .build();
    }
}
