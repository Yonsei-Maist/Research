package kr.ac.yonsei.maist.domain.command.dto;

import kr.ac.yonsei.maist.domain.command.domain.Command;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
public class CommandCreateRequestDto {

    private int sysLanguage;
    private int sysVoiceCommand;
    @NotEmpty
    private String command;

    public Command toEntity() {
        return Command.builder()
                .sysLanguage(sysLanguage)
                .sysVoiceCommand(sysVoiceCommand)
                .command(command)
                .build();
    }
}
