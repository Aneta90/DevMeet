package pl.com.devmeet.devmeet.member_associated.availability.domain;

public class AvailabilityCrudUpdater {

    private AvailabilityCrudRepository repository;

    public AvailabilityCrudUpdater(AvailabilityCrudRepository repository) {
        this.repository=repository;
    }


    public AvailabilityEntity updateEntity(AvailabilityDto oldDto, AvailabilityDto newDto) {
    }
}
