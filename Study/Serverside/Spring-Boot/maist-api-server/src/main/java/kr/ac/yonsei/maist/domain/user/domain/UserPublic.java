package kr.ac.yonsei.maist.domain.user.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_USER")
public class UserPublic {

    @Id
    @Column(name = "id_user")
    private int userId;

    @Column(name = "var_login_id")
    private String loginId;

    @Column(name = "var_name")
    private String name;

    @Column(name = "sys_sex")
    private Integer sex; //110(male) or 120(female)

    @Column(name = "date_birth")
    private Integer birth; //810 ~ 890
}
