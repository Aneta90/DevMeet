package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerInfoStatusEnum;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;

@NoArgsConstructor
@AllArgsConstructor
@Builder
class MessengerCrudDeleter {

    private MessengerCrudFinder messengerCrudFinder;
    private MessengerCrudSaver messengerCrudSaver;

    public MessengerEntity delete(MessengerDto messengerDto) throws MessengerNotFoundException, MessengerAlreadyExistsException {
        MessengerEntity messengerEntity = messengerCrudFinder.findEntity(messengerDto);

            if (messengerEntity.isActive()) {
                messengerEntity.setActive(false);

                return saveMessengerEntity(messengerEntity);
            }

            throw new MessengerAlreadyExistsException(MessengerInfoStatusEnum.MESSENGER_FOUND_BUT_NOT_ACTIVE.toString());
    }

    private MessengerEntity saveMessengerEntity(MessengerEntity entity) {
        return messengerCrudSaver.saveEntity(entity);
    }
}
