package kr.ac.yonsei.maist.domain.system.domain;

import kr.ac.yonsei.maist.domain.system.dto.SystemUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "TBL_SYS_CODE")
public class System {

    @Id
    @Column(name = "id_sys_code")
    private int sysCodeId;

    @Column(name = "var_domain")
    private String domain;

    @Column(name = "var_description")
    private String description;
    @Column(name = "int_depth1")
    private int depth1;

    @Column(name = "int_depth2")
    private int depth2;

    @Column(name = "int_depth3")
    private int depth3;

    @Column(name = "date_create")
    private String createDate;

    @Column(name = "date_edit")
    private String editDate;


    @Builder
    public System(int sysCodeId,
                  String domain,
                  String description,
                  int depth1,
                  int depth2,
                  int depth3) {
        this.sysCodeId = sysCodeId;
        this.domain = domain;
        this.description = description;
        this.depth1 = depth1;
        this.depth2 = depth2;
        this.depth3 = depth3;
    }

    public void update(SystemUpdateRequestDto dto) {
        this.domain = dto.getDomain();
        this.description = dto.getDescription();
        this.depth1 = dto.getDepth1();
        this.depth2 = dto.getDepth2();
        this.depth3 = dto.getDepth3();
    }
}
