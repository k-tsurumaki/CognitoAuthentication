package com.example.CognitoAuthentication.repository;

import com.example.CognitoAuthentication.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest // @Repositoryに関するテストを行うためのアノテーション。@Serviceや@Componentは初期化されない。
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // テストで使用するデータベースの設定を自動で行うためのアノテーション。今回はMySQLを用いるためNONE
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // 各テストが実行された後にコンテキストがリセットされる。
public class ItemRepositoryTest {

	@Autowired
	private ItemRepository itemRepository;

	@Test
	public void testSoftDelete_ShouldUpdateDeletedFlg() {
		Long itemId = 1L;
		// softDeleteメソッドを呼び出して、deletedFlgをtrueに変更
		int updatedRows = itemRepository.softDelete(itemId);

		// 1行が更新されたことを確認
		assertEquals(1, updatedRows);

		// 更新後のアイテムを取得して、deletedFlgがtrueに更新されているかを確認
		Item updatedItem = itemRepository.findById(itemId).orElse(null);
		assertNotNull(updatedItem);
		assertTrue(updatedItem.getDeletedFlg());
	}

	@Test
	public void testSoftDelete_ItemNotFound() {
		// 存在しないitemIdでsoftDeleteを実行
		int updatedRows = itemRepository.softDelete(999L);

		// 0行が更新されることを確認
		assertEquals(0, updatedRows);
	}
}
