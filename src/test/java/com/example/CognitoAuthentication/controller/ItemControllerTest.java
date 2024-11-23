package com.example.CognitoAuthentication.controller;

import com.example.CognitoAuthentication.dto.ItemCreateRequestDTO;
import com.example.CognitoAuthentication.dto.ItemDTO;
import com.example.CognitoAuthentication.dto.ItemUpdateRequestDTO;
import com.example.CognitoAuthentication.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.example.CognitoAuthentication.dto.ItemDTO;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

	@Mock
	private ItemService itemService;

	@InjectMocks
	private ItemController itemController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
	}

	@Test
	void testGetAllItems() throws Exception {
		// モックのアイテムリスト
		ItemDTO itemDTO1 = new ItemDTO(1L, "Item1", "Category1");
		ItemDTO itemDTO2 = new ItemDTO(2L, "Item2", "Category2");

		given(itemService.getAllItems()).willReturn(Arrays.asList(itemDTO1, itemDTO2));

		// GETリクエストを送信して結果を確認
		mockMvc.perform(get("/items"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].itemId").value(1))
				.andExpect(jsonPath("$[1].itemName").value("Item2"));

		verify(itemService).getAllItems();  // serviceメソッドが呼ばれたことを確認
	}

	@Test
	void testCreateItem() throws Exception {
		// モックの入力DTOと結果DTO
		ItemCreateRequestDTO createRequestDTO = new ItemCreateRequestDTO("NewItem", "NewCategory");
		ItemDTO createdItemDTO = new ItemDTO(1L, "NewItem", "NewCategory");

		given(itemService.createItem(createRequestDTO)).willReturn(createdItemDTO);

		// POSTリクエストを送信して結果を確認
		mockMvc.perform(post("/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"itemName\": \"NewItem\", \"itemCategory\": \"NewCategory\" }"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.itemId").value(1))
				.andExpect(jsonPath("$.itemName").value("NewItem"));

		verify(itemService).createItem(createRequestDTO);  // serviceメソッドが呼ばれたことを確認
	}

	@Test
	void testGetItemById() throws Exception {
		// モックのDTO
		ItemDTO itemDTO = new ItemDTO(1L, "Item1", "Category1");

		given(itemService.getItemById(1L)).willReturn(itemDTO);

		// GETリクエストを送信して結果を確認
		mockMvc.perform(get("/items/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.itemId").value(1))
				.andExpect(jsonPath("$.itemName").value("Item1"));

		verify(itemService).getItemById(1L);  // serviceメソッドが呼ばれたことを確認
	}

	@Test
	void testUpdateItem() throws Exception {
		// モックの入力DTOと結果DTO
		ItemUpdateRequestDTO updateRequestDTO = new ItemUpdateRequestDTO("UpdatedItem", "UpdatedCategory");
		ItemDTO updatedItemDTO = new ItemDTO(1L, "UpdatedItem", "UpdatedCategory");

		given(itemService.updateItem(1L, updateRequestDTO)).willReturn(updatedItemDTO);

		// PUTリクエストを送信して結果を確認
		mockMvc.perform(put("/items/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"itemName\": \"UpdatedItem\", \"itemCategory\": \"UpdatedCategory\" }"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.itemId").value(1))
				.andExpect(jsonPath("$.itemName").value("UpdatedItem"));

		verify(itemService).updateItem(1L, updateRequestDTO);  // serviceメソッドが呼ばれたことを確認
	}

	@Test
	void testDeleteItem() throws Exception {
		// DELETEリクエストを送信して結果を確認
		mockMvc.perform(delete("/items/1"))
				.andExpect(status().isNoContent());

		verify(itemService).deleteItem(1L);  // serviceメソッドが呼ばれたことを確認
	}
}
