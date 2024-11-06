CREATE TABLE IF NOT EXISTS order_entity
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_code VARCHAR(255) NOT NULL,
    status       VARCHAR(255),
    total_value  NUMERIC(19, 2)
);

CREATE TABLE IF NOT EXISTS product_entity
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255)   NOT NULL,
    quantity     INT            NOT NULL,
    unit_price   NUMERIC(19, 2) NOT NULL,
    order_id     BIGINT,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id)
            REFERENCES order_entity (id)
            ON DELETE CASCADE
);