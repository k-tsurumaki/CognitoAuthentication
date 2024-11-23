package com.example.CognitoAuthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemUpdateRequestDTO {
	private String itemName;
	private String itemCategory;
}
