CREATE TABLE products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE warehouse_stocks (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    warehouse_type VARCHAR(50) NOT NULL,
    process_type VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_product FOREIGN KEY(product_id) REFERENCES products(id)
);

-- Insert sample data for testing
INSERT INTO products (name) VALUES
('Baju Bayi Model A'),
('Celana Bayi Model B');

-- Insert initial stock data
INSERT INTO warehouse_stocks (product_id, quantity, warehouse_type, process_type, version) VALUES
(1, 500, 'RAW', NULL, 0),
(2, 800, 'RAW', NULL, 0),
(1, 50, 'WIP', 'CUTTING', 0),
(1, 20, 'WIP', 'SAWING', 0),
(1, 150, 'FINISHED', NULL, 0);
