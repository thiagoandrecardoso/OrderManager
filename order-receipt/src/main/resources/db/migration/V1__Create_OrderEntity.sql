CREATE TABLE order_entity
(
    id           SERIAL PRIMARY KEY,
    product_code VARCHAR(255) NOT NULL,
    status       VARCHAR(255)
);