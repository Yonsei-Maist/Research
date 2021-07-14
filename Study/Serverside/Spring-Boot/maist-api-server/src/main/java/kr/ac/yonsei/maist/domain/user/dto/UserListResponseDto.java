package kr.ac.yonsei.maist.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonFormat
@Getter
@NoArgsConstructor
public class UserListResponseDto {

    @JsonIgnore
    private int userNxId;
    @JsonProperty
    private int userId;
    @JsonProperty
    private String name;
    @JsonProperty
    private int sysSex; //110(male) or 120(female)
    @JsonProperty
    private int sysAge; //810 ~ 890
    @JsonProperty
    private int sysHeight; //710 ~ 790
    @JsonProperty
    private String createDate;
    @JsonProperty
    private String editDate;
    @JsonProperty
    private String profileId;

}
