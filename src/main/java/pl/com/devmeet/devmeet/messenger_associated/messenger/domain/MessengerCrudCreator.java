package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

public class MessengerCrudCreator {

    private MessengerCrudFinder messengerCrudFinder;
    private MessengerCrudSaver messengerCrudSaver;

    public MessengerCrudCreator(MessengerRepository messengerRepository) {
        this.messengerCrudFinder = new MessengerCrudFinder(messengerRepository);
        this.messengerCrudSaver = new MessengerCrudSaver(messengerRepository);
    }

    public MessengerDto create(MessengerDto messengerDto) throws EntityNotFoundException, EntityAlreadyExistsException {

        if (messengerCrudFinder.isExist(messengerDto)) {
            throw new EntityAlreadyExistsException("This group already has it's own messenger");
        } else {
            messengerDto.setActive(true);
            return saveMessengerEntity(MessengerCrudFacade.map(messengerDto));
        }
    }


    private MessengerDto saveMessengerEntity(MessengerEntity messengerDto){
        return messengerCrudSaver.saveEntity(messengerDto);
    }
}