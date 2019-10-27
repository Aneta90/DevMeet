package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupDto;

import java.util.List;
import java.util.Optional;

public class MessengerCrudFinder {

    private MessengerRepository messengerRepository;

    MessengerCrudFinder(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }

    public Optional<MessengerEntity> findEntity(GroupDto groupDto) throws IllegalArgumentException, EntityNotFoundException {
        Optional<MessengerEntity> messengerEntity;
        if (groupDto.getMessenger() == null) {
            throw new EntityNotFoundException("Given group does not have any messenger yet.");
        } else {
            messengerEntity = messengerRepository.findMessengerByGroup(groupDto.getGroupName());
        }
        return messengerEntity;
    }

    public MessengerDto read(GroupDto groupDto) throws EntityNotFoundException {
        return getDtoFromEntity(findEntity(groupDto).get());
    }

    MessengerDto getDtoFromEntity(MessengerEntity messengerEntity) {
        return MessengerCrudInterface.map(messengerEntity);
    }

    List<MessengerEntity> findEntities(MessengerDto messengerDto) throws IllegalArgumentException {
        return null;
    }

    public boolean isExist(MessengerDto messengerDto) throws EntityNotFoundException {
        return findEntity(messengerDto.getGroup()) != null;
    }
}