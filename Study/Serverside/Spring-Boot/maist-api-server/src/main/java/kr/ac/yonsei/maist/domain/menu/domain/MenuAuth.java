/**
 * domain structure of Menu authorization
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
@Table(name = "TBL_MENU_AUTH")
public class MenuAuth {

    /**
     * menu auth id (primary key)
     */
    @Id
    @Column(name = "id_menu_auth")
    private int id;

    /**
     * menu id
     */
    @Column(name = "id_menu")
    private String menuId;

    /**
     * level information
     */
    @Column(name = "sys_level")
    private int level;

    /**
     * create date
     */
    @Column(name = "date_create")
    private String createDate;

    /**
     * edit date
     */
    @Column(name = "date_edit")
    private String editDate;

    @Builder
    public MenuAuth(String menuId, int level) {
        this.menuId = menuId;
        this.level = level;
    }
}
