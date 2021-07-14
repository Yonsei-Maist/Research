package kr.ac.yonsei.maist.domain.user.dao;

import static kr.ac.yonsei.maist.domain.user.domain.QUserPrivate.userPrivate;
import static kr.ac.yonsei.maist.domain.machine.domain.QMachine.machine;
import static kr.ac.yonsei.maist.domain.userMachine.domain.QUserMachine.userMachine;
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
                                JPAExpressions.select(system.content)
                                .from(system)
                                .where(system.sysCodeId.eq(userPrivate.sex)),
                                "sex"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.content)
                                        .from(system)
                                        .where(system.sysCodeId.eq(userPrivate.age)),
                                "age"),
                        ExpressionUtils.as(
                                JPAExpressions.select(system.content)
                                        .from(system)
                                        .where(system.sysCodeId.eq(userPrivate.height)),
                                "height")
                ))
                .from(userPrivate)
                .where(userPrivate.userId.eq(userId))
                .fetch();
    }

    /**
     * Gets a list of users connected by machine ID.
     * @param machineId machine id
     * @return user list
     */
    public List<UserListResponseDto> findUserByMachineId(String machineId) {
        return queryFactory
                .select(Projections.fields(UserListResponseDto.class,
                        userPrivate.userId.as("userId"),
                        userPrivate.name.as("name"),
                        userPrivate.sex.as("sysSex"),
                        userPrivate.age.as("sysAge"),
                        userPrivate.height.as("sysHeight"),
                        userPrivate.createDate.as("createDate"),
                        userPrivate.editDate.as("editDate"),
                        userPrivate.profileId.as("profileId"),
                        userMachine.userNxId.as("userNxId")
                ))
                .from(userPrivate)
                .join(userMachine).on(userPrivate.userId.eq(userMachine.userId))
                .join(machine).on(userMachine.machineId.eq(machine.id))
                .where(machine.id.eq(machineId))
                .fetch();
    }

    public UserResponseDto findUserByMachineIdAndName(String machineId, String name) {
        return queryFactory
                .select(Projections.fields(UserResponseDto.class,
                        userPrivate.userId.as("userId"),
                        userPrivate.name.as("name"),
                        userPrivate.sex.as("sysSex"),
                        userPrivate.age.as("sysAge"),
                        userPrivate.height.as("sysHeight")
                ))
                .from(userPrivate)
                .join(userMachine).on(userPrivate.userId.eq(userMachine.userId))
                .join(machine).on(userMachine.machineId.eq(machine.id))
                .where(machine.id.eq(machineId).and(userPrivate.name.eq(name)))
                .fetchOne();
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
     * @param name user name
     * @return User instance if exists
     */
    public UserPrivate findUserByIdAndUserName(String id, String name) {
        return queryFactory
                .select(userPrivate)
                .from(userPrivate)
                .where(userPrivate.name.eq(name)
                        .and(userPrivate.userId.in(
                                JPAExpressions.select(userMachine.userId)
                                        .from(machine)
                                        .join(userMachine).on(machine.id.eq(userMachine.machineId))
                                        .where(machine.id.eq(id)))))
                .fetchOne();
    }
}
