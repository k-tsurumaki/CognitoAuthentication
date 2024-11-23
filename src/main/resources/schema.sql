-- テーブルが存在する場合は削除
DROP TABLE IF EXISTS m_item;

CREATE TABLE m_item (
    item_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 主キー
    item_name VARCHAR(255) NOT NULL,          -- アイテム名
    item_category VARCHAR(255) NOT NULL,      -- アイテムカテゴリ
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- 作成日時
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新日時
    deleted_flg BOOLEAN DEFAULT FALSE NOT NULL -- 削除フラグ
);