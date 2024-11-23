package com.example.CognitoAuthentication.service;

import com.example.CognitoAuthentication.dto.ItemCreateRequestDTO;
import com.example.CognitoAuthentication.dto.ItemDTO;
import com.example.CognitoAuthentication.dto.ItemUpdateRequestDTO;
import com.example.CognitoAuthentication.entity.Item;
import com.example.CognitoAuthentication.exception.ItemNotFoundException;
import com.example.CognitoAuthentication.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
public class ItemServiceTest {

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private ItemService itemService;

	private Item item;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		item = Item.builder()
				.itemId(1L)
				.itemName("Sample Item")
				.itemCategory("Sample Category")
				.build();
	}

	@Test
	void testGetAllItems() {
		// Prepare mock response
		when(itemRepository.findByDeletedFlgFalse()).thenReturn(List.of(item));

		// Call the service method
		List<ItemDTO> result = itemService.getAllItems();

		// Assert the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(item.getItemId(), result.get(0).getItemId());
		assertEquals(item.getItemName(), result.get(0).getItemName());
		assertEquals(item.getItemCategory(), result.get(0).getItemCategory());

		// Verify repository interaction
		verify(itemRepository, times(1)).findByDeletedFlgFalse();
	}

	@Test
	void testCreateItem() {
		// Prepare mock request DTO
		ItemCreateRequestDTO requestDTO = ItemCreateRequestDTO.builder()
				.itemName("New Item")
				.itemCategory("New Category")
				.build();

		// Prepare mock response
		when(itemRepository.save(any(Item.class))).thenReturn(item);

		// Call the service method
		ItemDTO result = itemService.createItem(requestDTO);

		// Assert the result
		assertNotNull(result);
		assertEquals(item.getItemId(), result.getItemId());
		assertEquals(item.getItemName(), result.getItemName());
		assertEquals(item.getItemCategory(), result.getItemCategory());

		// Verify repository interaction
		verify(itemRepository, times(1)).save(any(Item.class));
	}

	@Test
	void testGetItemById_ItemFound() {
		// Prepare mock response
		when(itemRepository.findById(item.getItemId())).thenReturn(Optional.of(item));

		// Call the service method
		ItemDTO result = itemService.getItemById(item.getItemId());

		// Assert the result
		assertNotNull(result);
		assertEquals(item.getItemId(), result.getItemId());
		assertEquals(item.getItemName(), result.getItemName());
		assertEquals(item.getItemCategory(), result.getItemCategory());

		// Verify repository interaction
		verify(itemRepository, times(1)).findById(item.getItemId());
	}

	@Test
	void testGetItemById_ItemNotFound() {
		// Prepare mock response
		when(itemRepository.findById(item.getItemId())).thenReturn(Optional.empty());

		// Call the service method and assert exception
		assertThrows(ItemNotFoundException.class, () -> itemService.getItemById(item.getItemId()));

		// Verify repository interaction
		verify(itemRepository, times(1)).findById(item.getItemId());
	}

	@Test
	void testUpdateItem() {
		// Prepare mock request DTO
		ItemUpdateRequestDTO updateRequestDTO = ItemUpdateRequestDTO.builder()
				.itemName("Updated Item")
				.itemCategory("Updated Category")
				.build();

		// Prepare mock response
		when(itemRepository.findById(item.getItemId())).thenReturn(Optional.of(item));
		when(itemRepository.save(any(Item.class))).thenReturn(item);

		// Call the service method
		ItemDTO result = itemService.updateItem(item.getItemId(), updateRequestDTO);

		// Assert the result
		assertNotNull(result);
		assertEquals(item.getItemId(), result.getItemId());
		assertEquals("Updated Item", result.getItemName());
		assertEquals("Updated Category", result.getItemCategory());

		// Verify repository interaction
		verify(itemRepository, times(1)).findById(item.getItemId());
		verify(itemRepository, times(1)).save(any(Item.class));
	}

	@Test
	void testDeleteItem_ItemNotFound() {
		// Prepare mock response
		when(itemRepository.softDelete(item.getItemId())).thenReturn(0);

		// Call the service method and assert exception
		assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(item.getItemId()));

		// Verify repository interaction
		verify(itemRepository, times(1)).softDelete(item.getItemId());
	}

	@Test
	void testDeleteItem_ItemDeleted() {
		// Prepare mock response
		when(itemRepository.softDelete(item.getItemId())).thenReturn(1);

		// Call the service method
		itemService.deleteItem(item.getItemId());

		// Verify repository interaction
		verify(itemRepository, times(1)).softDelete(item.getItemId());
	}
}
