package kr.ac.yonsei.maist.domain.user.dto;

import kr.ac.yonsei.maist.domain.menu.dto.MenuDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInformationDto {

    private int userId;
    private String name;
    private String token;
    private List<MenuDto> menuList;

    public UserInformationDto (int userId, String name, List<MenuDto> menuList) {
        this.userId = userId;
        this.name = name;
        this.menuList = menuList;
    }
}
