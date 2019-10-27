package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberNotFoundException;

import java.util.Optional;

public class MessengerCrudDeleter {

    private MessengerCrudFinder messengerCrudFinder;
    private MessengerCrudSaver messengerCrudSaver;

    public MessengerCrudDeleter(MessengerRepository messengerRepository) {
        this.messengerCrudFinder = new MessengerCrudFinder(messengerRepository);
        this.messengerCrudSaver = new MessengerCrudSaver(messengerRepository);
    }

    boolean delete(MessengerDto messengerDto) throws IllegalArgumentException, EntityNotFoundException {

        Optional<MessengerEntity> messengerEntity = messengerCrudFinder.findEntity(messengerDto.getGroup());
        if (messengerEntity.isPresent()) {

            messengerEntity.get().setActive(false);

            return saveMessengerEntity(messengerEntity.get()) != null;
        } else {
            throw new MemberNotFoundException("Member is not found in database or is not active");
        }
    }

    private MessengerDto saveMessengerEntity(MessengerEntity entity) throws EntityNotFoundException {
        return messengerCrudSaver.saveEntity(entity);
    }
}
