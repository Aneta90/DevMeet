package pl.com.devmeet.devmeet.domain.place;

import lombok.*;
import org.joda.time.DateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PlaceDto {

    private String placeUserName;
    private String placeDescription;
    private String placeWebsite;
    private String location;
    private DateTime creationTime;
    private boolean isActive;

}
