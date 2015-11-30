package kr.pe.lahuman.user;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by lahuman on 2015. 11. 30..
 */
public class UserDto {

    @Data
    public static class Create{
        private String id;
        @NotBlank
        @Email
        private String email;

        @NotNull
        private Integer age;
    }

    @Data
    public static class Response{
        private String id;
        private String email;
        private Integer age;
        private Date joined;
        private Date updated;
    }

    @Data
    public static class Update{
        @Email
        private String email;
        private Integer age;
    }
}
