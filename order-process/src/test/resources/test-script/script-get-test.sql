-- Insert data into order_entity
INSERT INTO order_entity (product_code, status, total_value)
VALUES ('P001', 0, 100.00),
       ('P002', 0, 200.00),
       ('P003', 0, 300.00);

-- Insert data into product_entity
INSERT INTO product_entity (product_name, quantity, unit_price, order_id)
VALUES ('Product A', 2, 50.00, 1),
       ('Product B', 1, 200.00, 2),
       ('Product C', 3, 100.00, 3);