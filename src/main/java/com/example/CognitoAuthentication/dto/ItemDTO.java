package com.example.CognitoAuthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
@Builder
@AllArgsConstructor
public class ItemDTO {
	private Long itemId;
	private String itemName;
	private String itemCategory;
}
