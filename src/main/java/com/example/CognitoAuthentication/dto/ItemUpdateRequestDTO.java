package com.example.CognitoAuthentication.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemUpdateRequestDTO {
	private String itemName;
	private String itemCategory;
}
