/**
 * Service that performs all logic such as inquiry, creation, modification, deletion of user
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.ac.yonsei.maist.domain.user.service;

import kr.ac.yonsei.maist.domain.user.dao.UserPublicRepository;
import kr.ac.yonsei.maist.domain.user.dao.UserQueryFactory;
import kr.ac.yonsei.maist.domain.user.dao.UserPrivateRepository;
import kr.ac.yonsei.maist.domain.user.domain.UserPrivate;
import kr.ac.yonsei.maist.domain.user.domain.UserPublic;
import kr.ac.yonsei.maist.domain.user.dto.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    @NonNull
    private final UserPrivateRepository userPrivateRepository;
    @NonNull
    private final UserPublicRepository userPublicRepository;
    @Autowired
    private UserQueryFactory userQueryFactory;

    /**
     * Create a user.
     * @param dto user information
     */
    @Transactional
    public UserPrivate createUser(UserCreateRequestDto dto) throws Exception {
        UserPrivate user = userPrivateRepository.save(dto.toEntity());

        return user;
    }

    /**
     * Update user information.
     * @param id machine id
     * @param dto user information
     */
    @Transactional
    public void updateUser(String id, UserUpdateRequestDto dto) throws Exception {
        UserPrivate entity = userQueryFactory.findUserById(id);
        if(entity == null) {
            throw new IllegalArgumentException("id="+id);
        }

        entity.update(dto);
    }

    /**
     * Get user information.
     * @param userId user id
     * @return user information
     */
    @Transactional
    public UserPrivate findByUserId(int userId) throws Exception {
        UserPrivate entity = userPrivateRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("id="+userId));
        return entity;
    }

    /**
     * Get user information joined with the system code.
     * @param userId user id
     * @return user information
     */
    @Transactional
    public List<UserResponseDto> findUserByUserId(int userId) throws Exception {
        List<UserResponseDto> user = userQueryFactory.findUserByUserId(userId);

        return user;
    }

    /**
     * Find user using user id and password.
     * @param userId user id
     * @param pwd user password
     * @return User instance if exists
     */
    public UserPrivate findByIdAndPassword(int userId, String pwd) throws Exception {
        UserPrivate entity = userQueryFactory.findByUserIdAndPwd(userId, pwd);
        if(entity == null) {
            throw new IllegalArgumentException("userId="+userId);
        }
        return entity;
    }

    /**
     * Check whether the user ID exists.
     * @param userId User Id
     * @return UserExistResponseDto
     */
    @Transactional
    public UserExistResponseDto userExist(int userId) {
        UserPrivate entity = userPrivateRepository.findById(userId)
                .orElseGet(UserPrivate::new);

        UserExistResponseDto dto = new UserExistResponseDto(false);
        if(entity!= null) {
            dto.setIsExisted(true);
        }
        return dto;
    }

    /**
     * Create a user.
     * @param dto SignUpRequestDto
     */
    @Transactional
    public void save(SignUpRequestDto dto) throws Exception {
        userPrivateRepository.save(dto.toEntity());
    }

    /**
     * Verify the user's password.
     * @param userId User ID
     * @param password User password
     * @return UserExistResponseDto
     */
    @Transactional
    public UserExistResponseDto userPasswordExist(int userId, String password) {
        UserPrivate entity = userQueryFactory.findByUserIdAndPwd(userId, password);

        UserExistResponseDto dto = new UserExistResponseDto(false);
        if(entity != null){
            dto.setIsExisted(true);
        }
        return dto;
    }

    /**
     * Update user password.
     * @param userId User Id
     * @param dto UserPwdUpdateRequestDto
     */
    @Transactional
    public void update(int userId, UserPwdUpdateRequestDto dto) throws Exception {
        UserPrivate entity = userPrivateRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("id="+userId));

        entity.updatePassword(dto);
    }
}
