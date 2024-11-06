CREATE TABLE product_entity
(
    id           SERIAL PRIMARY KEY,
    product_name VARCHAR(255)   NOT NULL,
    quantity     INT            NOT NULL,
    unit_price   NUMERIC(19, 2) NOT NULL,
    order_id     BIGINT,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id)
            REFERENCES order_entity (id)
            ON DELETE CASCADE
);