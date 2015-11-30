package kr.pe.lahuman.user;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lahuman on 2015. 12. 1..
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/create/{id}", method= RequestMethod.POST)
    public ResponseEntity createUser(@PathVariable String id, @RequestBody @Valid UserDto.Create dto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        dto.setId(id);
        User user = userService.createUser(dto);
        return new ResponseEntity(modelMapper.map(user, UserDto.Response.class), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto.Response> getUsers(){
        return userService.list().stream()
                .map(user->modelMapper.map(user, UserDto.Response.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public UserDto.Response getUser(@PathVariable String id){
        return modelMapper.map(userService.getUser(id), UserDto.Response.class);
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody @Valid UserDto.Update dto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        User user = userService.updateUser(id, dto);
        return new ResponseEntity(modelMapper.map(user, UserDto.Response.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(UserDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserDuplicatedException(UserDuplicatedException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getId() + "] 중복된 ID 입니다.");
        errorResponse.setCode("duplicated.id.exception");
        return errorResponse;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccountNotFoundException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getId() + "]에 해당하는 계정이 없습니다.");
        errorResponse.setCode("user.not.found.exception");
        return errorResponse;
    }

    @Data
    private class ErrorResponse {
        private String message;
        private String code;
    }
}
