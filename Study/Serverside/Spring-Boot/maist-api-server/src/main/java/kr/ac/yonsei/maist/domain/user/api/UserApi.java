/**
 * Controller that performs all logic such as inquiry, creation, modification, deletion of user
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.04.27
 */
package kr.ac.yonsei.maist.domain.user.api;

import kr.ac.yonsei.maist.domain.menu.dto.MenuDto;
import kr.ac.yonsei.maist.global.response.dataMessage.GeneralDataMessage;
import kr.ac.yonsei.maist.global.response.ResponseMessage;
import kr.ac.yonsei.maist.global.config.security.JwtTokenProvider;
import kr.ac.yonsei.maist.domain.menu.service.MenuService;
import kr.ac.yonsei.maist.domain.user.domain.UserPrivate;
import kr.ac.yonsei.maist.domain.user.dto.*;
import kr.ac.yonsei.maist.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApi {

    private final UserService userService;
    private final MenuService menuService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Login user.
     * @param dto id, password
     * @return ResponseEntity<ResponseDataMessage> token
     */
    @PostMapping("/maist/login")
    public ResponseEntity<GeneralDataMessage> login(@Valid @RequestBody LoginRequestDto dto) throws Exception {
        UserPrivate user = userService.findByIdAndPassword(dto.getId(), dto.getPassword()); // 유효성 확인

        List<MenuDto> menuList = menuService.findById(user.getLevel());
        UserInformationDto dtoUser = new UserInformationDto(user.getUserId(), user.getName(), menuList);
        String token = jwtTokenProvider.createToken(dtoUser, dto.getLanguage()); // 토큰 발행

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(new LoginResponseDto(token))
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Create users on the web
     * @param dto user information
     * @return ResponseEntity<ResponseMessage> success or fail
     */
    @PostMapping("/maist/users")
    public ResponseEntity<ResponseMessage> createUser(@Valid @RequestBody UserCreateRequestDto dto) throws Exception {

        UserPrivate user = userService.createUser(dto); // user 생성

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Update user information.
     * @param id user login id
     * @param dto user information
     * @return ResponseEntity<ResponseMessage>
     */
    @PutMapping("/maist/users/{id}/{name}")
    public ResponseEntity<ResponseMessage> updateUser(@PathVariable String id,
                                                      @Valid @RequestBody UserUpdateRequestDto dto) throws Exception {

        userService.updateUser(id, dto);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Get user information.
     * @param userId user id
     * @return ResponseEntity<ResponseDataMessage> user information
     */
    @GetMapping("/maist/users/{userId}")
    public ResponseEntity<GeneralDataMessage> findUserByUserId(@PathVariable int userId) throws Exception {

        userService.findByUserId(userId); // 검증
        List<UserResponseDto> user = userService.findUserByUserId(userId);

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(user.get(0))
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Login user.
     * @param dto LoginRequestDto
     * @return ResponseEntity<ResponseDataMessage>
     */
/*    @PostMapping("/maist/web/login")
    public ResponseEntity<GeneralDataMessage> loginWeb(@Valid @RequestBody LoginWebRequestDto dto) throws Exception {
        UserPrivate user = userService.findByIdAndPassword(dto.getUserId(), dto.getPassword());
        List<MenuDto> menuList = menuService.findById(user.getLevel());

        UserInformationDto userDto = new UserInformationDto(user.getUserId(), user.getName(), menuList);
        String token = jwtTokenProvider.createToken(userDto, dto.getLanguage(), false); // 토큰 발행

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(new LoginResponseDto(token))
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }*/

    /**
     * Logout user.
     * @param dto LogoutRequestDto
     * @param request HttpServletRequest
     * @return ResponseEntity<ResponseMessage>
     */
    @PostMapping("/maist/logout")
    public ResponseEntity<ResponseMessage> logout(@Valid @RequestBody LogoutRequestDto dto, HttpServletRequest request) throws Exception {

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Check whether the user ID exists.
     * @param userId User Id
     * @return ResponseEntity<ResponseDataMessage> true or false
     */
    @GetMapping("/maist/users/id/{userId}")
    public ResponseEntity<GeneralDataMessage> userExist(@PathVariable int userId) throws Exception {
        UserExistResponseDto userExist = userService.userExist(userId);

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(userExist)
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Verify the user's password.
     * @param dto UserPwdExistRequestDto
     * @return ResponseEntity<ResponseDataMessage> true or false
     */
    @PostMapping("/maist/users/password")
    public ResponseEntity<GeneralDataMessage> userPasswordExist(@Valid @RequestBody UserPwdExistRequestDto dto) throws Exception {

        UserExistResponseDto userExist = userService.userPasswordExist(dto.getUserId(), dto.getPassword());

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(userExist)
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Update user password.
     * @param userId User Id
     * @param dto UserPwdUpdateRequestDto
     * @return ResponseEntity<ResponseMessage>
     */
    @PutMapping("/maist/users/password/{userId}")
    public ResponseEntity<ResponseMessage> updateUserPassword(@PathVariable int userId, @Valid @RequestBody UserPwdUpdateRequestDto dto) throws Exception {

        userService.update(userId, dto);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }
}
