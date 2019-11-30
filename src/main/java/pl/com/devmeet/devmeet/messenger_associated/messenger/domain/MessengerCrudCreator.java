package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerInfoStatusEnum;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;

@NoArgsConstructor
@Builder
class MessengerCrudCreator implements CrudEntityCreator<MessengerDto, MessengerEntity> {

    private MessengerCrudFinder messengerCrudFinder;
    private MessengerCrudSaver messengerCrudSaver;

    public MessengerCrudCreator(MessengerCrudFinder messengerCrudFinder, MessengerCrudSaver messengerCrudSaver) {
        this.messengerCrudFinder = messengerCrudFinder;
        this.messengerCrudSaver = messengerCrudSaver;
    }

    @Override
    public MessengerEntity createEntity(MessengerDto dto) throws MessengerAlreadyExistsException {
        MessengerEntity messenger;

        try {
            messenger = messengerCrudFinder.findEntity(dto);

            if (!messenger.isActive()) {
                return saveMessengerEntity(
                        setDefaultValuesWhenMessengerExists(messenger));
            }
        } catch (MessengerNotFoundException e) {
            return setDefaultValuesWhenMessengerNotExists(MessengerMapper.map(dto));
        }

        throw new MessengerAlreadyExistsException(MessengerInfoStatusEnum.MESSENGER_ALREADY_EXISTS.toString());
    }

    private MessengerEntity setDefaultValuesWhenMessengerNotExists(MessengerEntity entity) {
        entity.setActive(true);
        entity.setCreationTime(DateTime.now());
        return entity;
    }

    private MessengerEntity setDefaultValuesWhenMessengerExists(MessengerEntity entity) {
        return setDefaultValuesWhenMessengerNotExists(entity);
    }

    private MessengerEntity saveMessengerEntity(MessengerEntity messenger) {
        return messengerCrudSaver.saveEntity(messenger);
    }
}