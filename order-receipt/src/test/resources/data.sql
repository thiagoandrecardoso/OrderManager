INSERT INTO order_entity (product_code, status) VALUES ('ORDER123', 'RECEIVED');
INSERT INTO order_entity (product_code, status) VALUES ('ORDER456', 'SHIPPED');

INSERT INTO product_entity (product_name, quantity, unit_price, order_id) VALUES ('Product 1', 2, 10.50, 1);
INSERT INTO product_entity (product_name, quantity, unit_price, order_id) VALUES ('Product 2', 1, 20.00, 1);
INSERT INTO product_entity (product_name, quantity, unit_price, order_id) VALUES ('Product 3', 5, 15.00, 2);