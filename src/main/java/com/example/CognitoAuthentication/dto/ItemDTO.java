package com.example.CognitoAuthentication.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTO {
	private Long itemId;
	private String itemName;
	private String itemCategory;
}
