/**
 * Menu domain structure
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 */
package kr.ac.yonsei.maist.domain.menu.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TBL_MENU")
public class Menu {

    /**
     * menu id (primary key)
     */
    @Id
    @Column(name = "id_menu")
    private String menuId;

    /**
     * path of menu or API
     */
    @Column(name = "var_path")
    private String path;

    /**
     * menu name
     */
    @Column(name = "var_name")
    private String name;

    /**
     * check it is api or not
     */
    @Column(name = "char_is_api")
    private String strIsApi;

    /**
     * create time
     */
    @Column(name = "date_create")
    private String createDate;

    /**
     * edit time
     */
    @Column(name = "date_edit")
    private String editDate;

    @Builder
    public Menu(String menuId, String name, String path, String strIsApi) {
        this.menuId = menuId;
        this.name = name;
        this.path = path;
        this.strIsApi = strIsApi;
    }
}
