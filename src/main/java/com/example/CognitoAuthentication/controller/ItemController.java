package com.example.CognitoAuthentication.controller;

import com.example.CognitoAuthentication.dto.ItemCreateRequestDTO;
import com.example.CognitoAuthentication.dto.ItemDTO;
import com.example.CognitoAuthentication.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping
	public ResponseEntity<ItemDTO> postItem(@RequestBody @Valid ItemCreateRequestDTO itemCreateRequestDTO) {
		ItemDTO itemDTO = itemsService.getItem(itemCreateRequestDTO);
		return ResponseEntity.ok().body(itemDTO);
	}
}
