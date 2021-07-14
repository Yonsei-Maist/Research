/**
 * Service for menu
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 */
package kr.ac.yonsei.maist.domain.menu.service;

import kr.ac.yonsei.maist.domain.menu.dao.MenuQueryFactory;
import kr.ac.yonsei.maist.domain.menu.dao.MenuRepository;
import kr.ac.yonsei.maist.domain.menu.dto.MenuDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuService {

    @NonNull
    private final MenuRepository menuRepository;
    @Autowired
    private MenuQueryFactory menuQueryFactory;

    /**
     * Get menu list that allow to access user
     * @param userLevel User level
     * @return List of menu allow to access user
     */
    public List<MenuDto> findById(int userLevel) throws Exception {
        return menuQueryFactory.findAllByUserLevel(userLevel);
    }
}
