package kr.ac.yonsei.maist.domain.language.dto;

import kr.ac.yonsei.maist.domain.language.domain.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class LanguageCreateRequestDto {

    private int sysCodeId;
    private int sysLanguage;
    @NotEmpty
    private String content;

    public Language toEntity() {
        return Language.builder()
                .sysCode(sysCodeId)
                .languageKind(sysLanguage)
                .content(content)
                .build();
    }
}
