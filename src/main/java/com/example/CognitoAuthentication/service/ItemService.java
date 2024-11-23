package com.example.CognitoAuthentication.service;

import com.example.CognitoAuthentication.dto.ItemCreateRequestDTO;
import com.example.CognitoAuthentication.dto.ItemDTO;
import com.example.CognitoAuthentication.entity.Item;
import com.example.CognitoAuthentication.exception.ItemNotFoundException;
import com.example.CognitoAuthentication.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
	private final ItemRepository itemRepository;

	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public List<ItemDTO> getAllItems() {
		List<Item> items = itemRepository.findByDeletedFlgFalse();
		List<ItemDTO> itemDTOS = new ArrayList<>();
		for (Item item : items) {
			ItemDTO itemDTO = ItemDTO.builder()
					.itemId(item.getItemId())
					.itemName(item.getItemName())
					.itemCategory(item.getItemCategory())
					.build();
			itemDTOS.add(itemDTO);
		}
		return itemDTOS;
	}

	public ItemDTO addItem(@Valid ItemCreateRequestDTO itemCreateRequestDTO) {
		Item item = Item.builder()
				.itemName(itemCreateRequestDTO.getItemName())
				.itemCategory(itemCreateRequestDTO.getItemCategory())
				.build();
		item = itemRepository.save(item);
		ItemDTO itemDTO = ItemDTO.builder()
				.itemId(item.getItemId())
				.itemName(item.getItemName())
				.itemCategory(item.getItemCategory())
				.build();
		return itemDTO;
	}

	public ItemDTO getItem(Long itemId) {
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		ItemDTO itemDTO = ItemDTO.builder()
				.itemId(item.getItemId())
				.itemName(item.getItemName())
				.itemCategory(item.getItemCategory())
				.build();
		return itemDTO;
	}
}
