package kr.pe.lahuman.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lahuman on 2015. 11. 30..
 */
@Entity
@Data
public class User {

    @Id
    @Column(unique = true)
    private String id;
    private String email;
    private Integer age;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joined;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
