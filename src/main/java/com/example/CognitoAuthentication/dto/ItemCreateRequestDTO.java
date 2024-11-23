package com.example.CognitoAuthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ItemCreateRequestDTO {
	private String itemName;
	private String itemCategory;
}
