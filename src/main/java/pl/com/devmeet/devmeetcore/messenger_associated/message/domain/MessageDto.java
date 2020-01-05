package pl.com.devmeet.devmeetcore.messenger_associated.message.domain;

import lombok.*;
import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {

    private MessengerDto sender;

    private MessengerDto receiver;

    private String message;

    private DateTime modificationTime;

    private DateTime creationTime;

    private boolean isActive;
}