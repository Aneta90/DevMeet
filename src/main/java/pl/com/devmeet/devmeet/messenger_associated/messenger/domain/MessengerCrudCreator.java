package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeet.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeet.group_associated.group.domain.status_and_exceptions.GroupNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerAlreadyExistsException;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerArgumentNotSpecified;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerInfoStatusEnum;
import pl.com.devmeet.devmeet.messenger_associated.messenger.status_and_exceptions.MessengerNotFoundException;
import pl.com.devmeet.devmeet.user.domain.status_and_exceptions.UserNotFoundException;

@NoArgsConstructor
@AllArgsConstructor
@Builder
class MessengerCrudCreator implements CrudEntityCreator<MessengerDto, MessengerEntity> {

    private MessengerCrudFinder messengerCrudFinder;
    private MessengerCrudSaver messengerCrudSaver;

    @Override
    public MessengerEntity createEntity(MessengerDto dto) throws MessengerAlreadyExistsException, MessengerArgumentNotSpecified, MemberNotFoundException, UserNotFoundException, GroupNotFoundException {
        MessengerMemberOrGroupConnector messengerConnector = new MessengerMemberOrGroupConnector(messengerCrudFinder);
        MessengerEntity messenger;

        try {
            messenger = messengerCrudFinder.findEntity(dto);

            if (!messenger.isActive()) {
                return saveMessengerEntity(
                        setDefaultValuesWhenMessengerExists(messenger));
            }
        } catch (MessengerNotFoundException e) {

            return saveMessengerEntity(
                    setDefaultValuesWhenMessengerNotExists(
                            messengerConnector
                                    .connectWithMessenger(MessengerMapper.map(dto), dto)
                    ));
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