-- Create initial tables needed for application.

-- Create the user table
CREATE TABLE `user` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

-- Create the product table
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

-- Create the customer_order table
CREATE TABLE customer_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_order_number VARCHAR(255) NOT NULL UNIQUE,
    purchase_date DATETIME NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_customer_order_user_id
        FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- Create the customer_order_item table
CREATE TABLE customer_order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    quantity INT NOT NULL,
    price_per_item_at_purchase_time DECIMAL(10,2) NOT NULL,
    customer_order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_customer_order_item_customer_order_id
        FOREIGN KEY (customer_order_id) REFERENCES customer_order(id) ON DELETE CASCADE,
    CONSTRAINT fk_customer_order_item_product_id
        FOREIGN KEY (product_id) REFERENCES product(id)
);
