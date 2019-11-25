package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class MessengerCrudFinder implements CrudEntityFinder<MessengerDto,MessengerEntity> {

    private MessengerRepository messengerRepository;

    MessengerCrudFinder(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }

    public MessengerEntity findEntity(MessengerDto messengerDto) throws EntityNotFoundException {
        Optional<MessengerEntity> messenger = findMessenger(messengerDto);
        if (messenger.isPresent()) {
           return messenger.get();
        } else {
            throw new EntityNotFoundException("Given messenger does not have any group yet.");
        }
    }
    private Optional<MessengerEntity> findMessenger(MessengerDto messengerDto) {
        String messengerGroupName = messengerDto.getMessengerName();
        if (checkMessengerNick(messengerGroupName)) {
            return Optional.ofNullable(messengerRepository.findByMessengerName(messengerDto.getMessengerName()));
        }
        return Optional.empty();
    }

    private boolean checkMessengerNick(String messengerNick) {
        return messengerNick != null && !messengerNick.equals("");
    }


    public MessengerDto read(MessengerDto messengerDto) throws EntityNotFoundException {
        return getDtoFromEntity(findEntity(messengerDto));
    }

    private MessengerDto getDtoFromEntity(MessengerEntity messengerEntity) {
        return MessengerCrudInterface.map(messengerEntity);
    }

    public List<MessengerEntity> findEntities(MessengerDto messengerDto) throws IllegalArgumentException {
        return null;
    }

    public boolean isExist(MessengerDto messengerDto) {
        return messengerRepository.findByMessengerName(messengerDto.getMessengerName())!= null;
    }
}