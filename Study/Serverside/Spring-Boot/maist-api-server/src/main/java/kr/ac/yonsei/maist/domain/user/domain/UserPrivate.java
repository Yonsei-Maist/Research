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

    @Column(name = "enc_pwd")
    @ColumnTransformer(
            write = "passwordHash(?)")
    private String password;

    @Column(name = "var_name")
    private String name;

    @Column(name = "sys_sex")
    private Integer sex; //110(male) or 120(female)

    @Column(name = "sys_age")
    private Integer age; //810 ~ 890

    @Column(name = "sys_height")
    private Integer height; //710 ~ 790

    @Column(name = "date_create")
    private String createDate;

    @Column(name = "date_edit")
    private String editDate;

    @Column(name = "sys_level")
    private Integer level;

    @Column(name = "id_profile")
    private String profileId;

    @Builder
    public UserPrivate(String password,
                       String name,
                       int sex,
                       int age,
                       int height,
                       String profileId) {
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.profileId = profileId;
    }

    public void update(UserUpdateRequestDto dto) {
        this.name = dto.getName();
        this.sex = dto.getSex();
        this.age = dto.getAge();
        this.height = dto.getHeight();
    }

    public void updatePassword(UserPwdUpdateRequestDto dto) {
        this.password = dto.getPassword();
    }
}
