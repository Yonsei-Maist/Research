package kr.ac.yonsei.maist.domain.user.domain;

import kr.ac.yonsei.maist.domain.user.dto.UserPwdUpdateRequestDto;
import kr.ac.yonsei.maist.domain.user.dto.UserUpdateRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@DynamicInsert
@Table(name = "TBL_USER")
public class UserPrivate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int userId;

    @Column(name = "var_login_id")
    private String loginId;

    @Column(name = "enc_pwd")
    @ColumnTransformer(
            write = "passwordHash(?)")
    private String password;

    @Column(name = "var_name")
    private String name;

    @Column(name = "sys_sex")
    private Integer sex; //110(male) or 120(female)

    @Column(name = "date_birth")
    private String birth;

    @Column(name = "date_create")
    private String createDate;

    @Column(name = "date_edit")
    private String editDate;

    @Column(name = "sys_level")
    private Integer level;

    @Builder
    public UserPrivate(String password,
                       String name,
                       int sex,
                       String birth) {
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
    }

    public void update(UserUpdateRequestDto dto) {
        this.name = dto.getName();
        this.sex = dto.getSex();
        this.birth = dto.getBirth();
    }

    public void updatePassword(UserPwdUpdateRequestDto dto) {
        this.password = dto.getPassword();
    }
}
