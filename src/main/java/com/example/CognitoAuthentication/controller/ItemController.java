package com.example.CognitoAuthentication.controller;

import com.example.CognitoAuthentication.dto.ItemDTO;
import com.example.CognitoAuthentication.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
	private final ItemService itemsService;

	@Autowired
	public ItemController(ItemService itemsService) {
		this.itemsService = itemsService;
	}

	@GetMapping
	public ResponseEntity<List<ItemDTO>> getItems() {
		List<ItemDTO> itemDTOS = itemsService.getAllItems();
		return ResponseEntity.ok().body(itemDTOS);
	}
}
