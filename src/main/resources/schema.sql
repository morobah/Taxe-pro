CREATE TABLE produit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE,
    imported BOOLEAN,
    type VARCHAR(255)
);

CREATE TABLE recu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_taxes DOUBLE,
    total_price DOUBLE,
    pucharse_time TIMESTAMP
);

CREATE TABLE recu_produit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    recu_id BIGINT,
    quantity INT,
    price DOUBLE,
    sales_tax DOUBLE,
    FOREIGN KEY (product_id) REFERENCES produit(id),
    FOREIGN KEY (recu_id) REFERENCES recu(id)
);
