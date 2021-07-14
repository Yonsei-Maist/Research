/**
 * @Author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05
 * @Date 2021.01.03
 */
package kr.ac.yonsei.maist.domain.language.domain;

import kr.ac.yonsei.maist.domain.language.dto.LanguageUpdateRequestDto;
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
@Table(name = "TBL_LANGUAGE")
public class Language {

    @Id
    @Column(name = "id_language")
    private int lanId;

    @Column(name = "id_sys_code")
    private int sysCode;

    @Column(name = "sys_language")
    private int languageKind;

    @Column(name = "var_content")
    private String content;

    @Column(name = "date_create")
    private String createDate;

    @Column(name = "date_edit")
    private String editDate;

    @Builder
    public Language(int sysCode,
                    int languageKind,
                    String content) {
        this.sysCode = sysCode;
        this.languageKind = languageKind;
        this.content = content;
    }

    public void update(LanguageUpdateRequestDto dto) {
        this.sysCode = dto.getSysCodeId();
        this.languageKind = dto.getSysLanguage();
        this.content = dto.getContent();
    }
}
