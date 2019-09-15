package pl.com.devmeet.devmeet.domain.place;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PlaceDto {

    private String placeName;
    private String placeDescription;
    private Double gpsLatitude;
    private Double gpsLongitude;
    private boolean isActive;

}
