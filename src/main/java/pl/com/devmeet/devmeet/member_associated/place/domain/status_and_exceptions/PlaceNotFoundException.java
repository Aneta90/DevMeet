package pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 15.11.2019
 * Time: 23:57
 */
public class PlaceNotFoundException extends CrudException {
    public PlaceNotFoundException(String message) {
        super(message);
    }
}
