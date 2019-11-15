package pl.com.devmeet.devmeet.member_associated.place.domain.status_and_exceptions;

import pl.com.devmeet.devmeet.domain_utils.exceptions.CrudException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 00:01
 */
public class PlaceAlreadyExistsException extends CrudException {
    public PlaceAlreadyExistsException(String message) {
        super(message);
    }
}
