-- Insert data into order_entity
INSERT INTO order_entity (product_code, status, total_value)
VALUES ('P004', 0, 100.00),
       ('P005', 0, 200.00),
       ('P006', 0, 300.00);

-- Insert data into product_entity
INSERT INTO product_entity (product_name, quantity, unit_price, order_id)
VALUES ('Product A', 2, 50.00, 1),
       ('Product B', 1, 200.00, 2),
       ('Product C', 3, 100.00, 3);