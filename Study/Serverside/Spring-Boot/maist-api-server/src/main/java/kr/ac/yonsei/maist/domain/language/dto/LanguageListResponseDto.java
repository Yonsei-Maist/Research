package kr.ac.yonsei.maist.domain.language.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import kr.ac.yonsei.maist.domain.language.domain.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({"lanId", "sysCode", "languageKind", "content", "createDate", "editDate"})
@JsonFormat
@Getter
@Setter
@NoArgsConstructor
public class LanguageListResponseDto {

    @JsonProperty(value="languageId")
    private int lanId;
    @JsonProperty(value="sysCodeId")
    private int sysCode;
    @JsonProperty(value="sysLanguage")
    private int languageKind;
    private String content;
    private String createDate;
    private String editDate;

    public LanguageListResponseDto(Language entity) {
        this.lanId = entity.getLanId();
        this.sysCode = entity.getSysCode();
        this.languageKind = entity.getLanguageKind();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
        this.editDate = entity.getEditDate();
    }
}
