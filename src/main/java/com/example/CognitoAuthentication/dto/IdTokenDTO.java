package com.example.CognitoAuthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class IdTokenDTO {
	private String idToken;
	private Long userId;
}
