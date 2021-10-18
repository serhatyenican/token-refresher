package com.srt.roster.tokenRefresher.dto;

public class TokenDTO {
    public String access_token;
    public Integer expires_in;
    public String refresh_token;
    public Integer refresh_expires_in;
    public String token_type;
    public String session_state;
    public String scope;

    @Override
    public String toString() {
        return "TokenDTO{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", refresh_expires_in=" + refresh_expires_in +
                ", token_type='" + token_type + '\'' +
                ", session_state='" + session_state + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
