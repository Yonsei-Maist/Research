package kr.ac.yonsei.maist.domain.user.dao;

import static kr.ac.yonsei.maist.domain.user.domain.QUserPrivate.userPrivate;
import static kr.ac.yonsei.maist.domain.system.domain.QSystem.system;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.yonsei.maist.domain.user.domain.UserPrivate;
import kr.ac.yonsei.maist.domain.user.dto.UserListResponseDto;
import kr.ac.yonsei.maist.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserQueryFactory {

    private final JPAQueryFactory queryFactory;

    /**
     * Get user information.
     * @param userId user id
     * @return user information
     */
    public List<UserResponseDto> findUserByUserId(int userId) {
        return queryFactory
                .select(Projections.fields(UserResponseDto.class,
                        userPrivate.userId.as("userId"),
                        userPrivate.password.as("password"),
                        userPrivate.name.as("name"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.description)
                                .from(system)
                                .where(system.sysCodeId.eq(userPrivate.sex)),
                                "sex"),
                        userPrivate.birth.as("birth")
                ))
                .from(userPrivate)
                .where(userPrivate.userId.eq(userId))
                .fetch();
    }

    /**
     * Find User using id and password.
     * @param userId user id
     * @param pwd user password
     * @return User instance if exists
     */
    public UserPrivate findByUserIdAndPwd(int userId, String pwd) {
        return queryFactory
                .select(userPrivate)
                .from(userPrivate)
                .where(userPrivate.userId.eq(userId)
                        .and(userPrivate.password.eq(Expressions.stringTemplate("passwordHash({0})", pwd))))
                .fetchOne();
    }

    /**
     * Find User using machine id and user name.
     * @param id machine id
     * @return User instance if exists
     */
    public UserPrivate findUserById(String id) {
        return queryFactory
                .select(userPrivate)
                .from(userPrivate)
                .where(userPrivate.loginId.eq(id))
                .fetchOne();
    }
}
