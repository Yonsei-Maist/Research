package kr.ac.yonsei.maist.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@JsonFormat
@NoArgsConstructor
public class LoginResponseDto {

    @JsonProperty
    private String token;

    public LoginResponseDto(String token) {
        this.token = token;
    }
}
