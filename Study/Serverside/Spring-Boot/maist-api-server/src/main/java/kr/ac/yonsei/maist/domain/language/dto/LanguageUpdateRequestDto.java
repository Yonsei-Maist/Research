package kr.ac.yonsei.maist.domain.language.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class LanguageUpdateRequestDto {

    private int sysCodeId;
    private int sysLanguage;
    @NotEmpty
    private String content;
}
