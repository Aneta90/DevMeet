package pl.com.devmeet.devmeetcore.member_associated.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.status_and_exceptions.MemberNotFoundException;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.PlaceCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.PlaceDto;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.status_and_exceptions.PlaceNotFoundException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/places")
class PlaceApi {

    private PlaceCrudFacade place;

    @Autowired
    PlaceApi(PlaceCrudFacade place) {
        this.place = place;
    }

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getPlaces() throws MemberNotFoundException, PlaceNotFoundException, UserNotFoundException {
        return new ResponseEntity<>(place.findAll(), HttpStatus.OK);
    }
}
