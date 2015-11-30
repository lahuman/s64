package kr.pe.lahuman.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lahuman on 2015. 11. 30..
 */
public interface UserRepository extends JpaRepository<User, String>{
    User findById(final String id);
}
