package pl.com.devmeet.devmeet.domain_utils.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 16.11.2019
 * Time: 10:36
 */
public class AppLogicException extends CrudException {
    public AppLogicException(String message) {
        super(message);
    }
}
