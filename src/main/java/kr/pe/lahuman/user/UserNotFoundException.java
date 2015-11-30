package kr.pe.lahuman.user;

/**
 * Created by lahuman on 2015. 12. 1..
 */
public class UserNotFoundException extends UserDuplicatedException {
    public UserNotFoundException(String id) {
        super(id);
    }
}
