package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;

import java.util.List;

public class MessengerCrudFinder {

    MessengerRepository messengerRepository;

    public MessengerCrudFinder(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }


    public MessengerEntity findEntity(GroupDto groupDto) throws IllegalArgumentException, EntityNotFoundException { //Podstawową funkcjonalnością jest umieszczanie na stronie grupy wiadomości przez użytkowników, także sprawdzam, czy dana grupa ma swojego messengera

        MessengerEntity messengerEntity;
        if (groupDto.getMessenger() == null) {
            throw new EntityNotFoundException("Given group does not have any messenger yet.");
        } else {
            messengerEntity = messengerRepository.findMessengerByGroup(groupDto.getGroupName());
        }
        return messengerEntity;
    }

    public MessengerDto read(GroupDto groupDto) throws EntityNotFoundException {
        return getDtoFromEntity(findEntity(groupDto));
    }

    public MessengerDto getDtoFromEntity(MessengerEntity messengerEntity) {
        return MessengerCrudInterface.map(messengerEntity);
    }


    public List<MessengerEntity> findEntities(MessengerDto messengerDto) throws IllegalArgumentException, EntityNotFoundException {
        return null;
    }


    public boolean isExist(MessengerDto messengerDto) throws EntityNotFoundException {
        return findEntity(messengerDto.getGroup()) != null;
    }
}