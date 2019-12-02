package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
class MessageCrudDeleter {

    private MessageCrudFinder messageCrudFinder;
    private MessageCrudSaver messageCrudSaver;

    boolean delete(String memberNick) throws IllegalArgumentException, EntityNotFoundException {
// kasujemy wiadomości dla wybranego Membera
        return false;
    }
}