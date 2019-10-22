package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

public class MessengerCrudCreator {

    private MessengerCrudFinder messengerCrudFinder;
    private MessengerCrudSaver messengerCrudSaver;

    public MessengerCrudCreator(MessengerCrudFinder messengerCrudFinder, MessengerCrudSaver messengerCrudSaver) {
        this.messengerCrudFinder = messengerCrudFinder;
        this.messengerCrudSaver = messengerCrudSaver;
    }

    public MessengerDto create(MessengerDto messengerDto) throws EntityNotFoundException, EntityAlreadyExistsException {

        if (messengerCrudFinder.isExist(messengerDto)) {
            throw new EntityAlreadyExistsException("This group already has it's own messenger");
        }


    //    return saveMessengerEntity(MessengerCrudFacade.map(messengerDto)); do dorobienia MessengerCrudFacade
         return null;
    }


    private MessengerDto saveMessengerEntity(MessengerEntity messengerDto) throws EntityNotFoundException {
        return messengerCrudSaver.saveEntity(messengerDto);
    }


}
