package kr.pe.lahuman.user;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lahuman on 2015. 12. 1..
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public User createUser(UserDto.Create dto){
        User user = modelMapper.map(dto, User.class);

        String id = dto.getId();
        if(repository.findById(id) != null){
            log.error("user duplicated exception. {}", id);
            throw new UserDuplicatedException(id);
        }
        Date dt = new Date();
        user.setJoined(dt);
        return repository.save(user);
    }

    public User updateUser(String id, UserDto.Update dto){
        User user = getUser(id);
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setUpdated(new Date());
        return repository.save(user);
    }

    public void deleteUser(String id){
        repository.delete(getUser(id));
    }
    public List<User> list(){
        return repository.findAll();
    }
    public  User getUser(String id) {
        User user = repository.findById(id);
        if(user == null)
            throw new UserNotFoundException(id);
        return user;
    }
}
