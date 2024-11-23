package com.example.CognitoAuthentication.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "m_item")
@Getter
@NoArgsConstructor
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long itemId;

	@Column(name = "item_name", nullable = false)
	private String itemName;

	@Column(name = "item_category", nullable = false)
	private String itemCategory;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@Column(name = "deleted_flg", nullable = false)
	private Boolean deletedFlg = false;

	@Builder
	public Item(String itemName, String itemCategory) {
		this.itemName = itemName;
		this.itemCategory = itemCategory;
	}
}
