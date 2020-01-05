package pl.com.devmeet.devmeetcore.member_associated.place.domain.status_and_exceptions;

import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

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
