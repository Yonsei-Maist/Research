package kr.ac.yonsei.maist.domain.command.dto;

import kr.ac.yonsei.maist.domain.command.domain.Command;
import lombok.Getter;

@Getter
public class CommandListResponseDto {

    private int commandDataId;
    private int sysLanguage;
    private int sysVoiceCommand;
    private String command;
    private String learnDate;
    private String createDate;
    private String editDate;

    public CommandListResponseDto(Command entity) {
        this.commandDataId = entity.getCommandDataId();
        this.sysLanguage = entity.getSysLanguage();
        this.sysVoiceCommand = entity.getSysVoiceCommand();
        this.command = entity.getCommand();
        this.learnDate = entity.getLearnDate();
        this.createDate = entity.getCreateDate();
        this.editDate = entity.getEditDate();
    }
}
