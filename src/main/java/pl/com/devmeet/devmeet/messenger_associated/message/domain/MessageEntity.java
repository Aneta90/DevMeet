package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerEntity;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "messeges")
@Entity
public class MessageEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) //?? sprawdzić
    private MessengerEntity messenger;
}
