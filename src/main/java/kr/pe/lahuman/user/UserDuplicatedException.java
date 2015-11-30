package kr.pe.lahuman.user;

import lombok.Getter;

/**
 * Created by lahuman on 2015. 12. 1..
 */
public class UserDuplicatedException extends RuntimeException {
    @Getter
    private String id;
    public UserDuplicatedException(String id) {
        this.id = id;
    }


}
