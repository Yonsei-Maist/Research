package kr.ac.yonsei.maist.domain.command.domain;

import kr.ac.yonsei.maist.domain.command.dto.CommandUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "TBL_COMMAND_DATA")
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_command_data")
    private int commandDataId;

    @Column(name = "sys_language")
    private int sysLanguage;

    @Column(name = "sys_voice_command")
    private int sysVoiceCommand;

    @Column(name = "var_command")
    private String command;

    @Column(name = "date_learn")
    private String learnDate;

    @Column(name = "date_create")
    private String createDate;

    @Column(name = "date_edit")
    private String editDate;

    @Builder
    public Command(int sysLanguage,
                    int sysVoiceCommand,
                    String command) {
        this.sysLanguage = sysLanguage;
        this.sysVoiceCommand = sysVoiceCommand;
        this.command = command;
    }

    public void update(CommandUpdateRequestDto dto) {
        this.sysLanguage = dto.getSysLanguage();
        this.sysVoiceCommand = dto.getSysVoiceCommand();
        this.command = dto.getCommand();
    }
}
