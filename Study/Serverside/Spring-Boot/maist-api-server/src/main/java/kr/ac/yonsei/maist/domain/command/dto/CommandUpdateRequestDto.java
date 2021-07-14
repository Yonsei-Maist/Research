package kr.ac.yonsei.maist.domain.command.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CommandUpdateRequestDto {

    private int commandDataId;
    private int sysLanguage;
    private int sysVoiceCommand;
    @NotEmpty
    private String command;
}
