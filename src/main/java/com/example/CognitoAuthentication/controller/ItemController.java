package com.example.CognitoAuthentication.controller;

import com.example.CognitoAuthentication.dto.ItemCreateRequestDTO;
import com.example.CognitoAuthentication.dto.ItemDTO;
import com.example.CognitoAuthentication.dto.ItemUpdateRequestDTO;
import com.example.CognitoAuthentication.service.ItemService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
	private final ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping
	public ResponseEntity<List<ItemDTO>> getAllItems() {
		List<ItemDTO> itemDTOS = itemService.getAllItems();
		return ResponseEntity.ok().body(itemDTOS);
	}

	@PostMapping
	public ResponseEntity<ItemDTO> createItem(@RequestBody @Valid ItemCreateRequestDTO itemCreateRequestDTO) {
		ItemDTO itemDTO = itemService.createItem(itemCreateRequestDTO);
		return ResponseEntity.ok().body(itemDTO);
	}

	@GetMapping("/{item_id}")
	public ResponseEntity<ItemDTO> getItemById(@PathVariable("item_id") Long itemId){
		ItemDTO itemDTO = itemService.getItemById(itemId);
		return ResponseEntity.ok().body(itemDTO);
	}

	@PutMapping("/{item_id}")
	public ResponseEntity<ItemDTO> updateItem(@PathVariable("item_id") Long itemId, @RequestBody @Valid ItemUpdateRequestDTO itemUpdateRequestDTO){
		ItemDTO itemDTO = itemService.updateItem(itemId, itemUpdateRequestDTO);
		return ResponseEntity.ok().body(itemDTO);
	}

}
