package com.example.CognitoAuthentication.repository;

import com.example.CognitoAuthentication.entity.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	public List<Item> findByDeletedFlgFalse();

	@Transactional
	@Modifying
	@Query("update Item i set i.deletedFlg = true where i.itemId = :itemId")
	// 更新された行数を返す
	// クエリ内のitemIdにitemIdをバインド
	public int softDelete(@Param("itemId") Long itemId);
}
