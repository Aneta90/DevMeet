package pl.com.devmeet.devmeet.member_associated.place.domain;

import org.joda.time.DateTime;

import java.util.UUID;

public class PlaceEntity {

    private UUID Id;

    private String placeName;

    private String placeDescription;

    private String placeWebsite;

    private String location;

    private DateTime creationTime;

    private boolean isActive;
}
