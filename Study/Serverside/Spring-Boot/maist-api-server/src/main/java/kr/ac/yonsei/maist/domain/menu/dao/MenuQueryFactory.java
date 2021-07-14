/**
 * Menu query factory
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 */
package kr.ac.yonsei.maist.domain.menu.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.yonsei.maist.domain.menu.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.ac.yonsei.maist.domain.menu.domain.QMenu.menu;
import static kr.ac.yonsei.maist.domain.menu.domain.QMenuAuth.menuAuth;

@RequiredArgsConstructor
@Repository
public class MenuQueryFactory {

    private final JPAQueryFactory queryFactory;

    public List<MenuDto> findAllByUserLevel(int userLevel) {
        return queryFactory
                .select(Projections.fields(MenuDto.class,
                        menu.menuId.as("id"),
                        menu.path.as("path"),
                        menuAuth.level.as("level"),
                        menu.name.as("name")
                ))
                .from(menu)
                .join(menuAuth).on(menu.menuId.eq(menuAuth.menuId))
                .where(menuAuth.level.eq(userLevel))
                .fetch();
    }
}
