CREATE TABLE stockcontrol.products(
  id INT(20) NOT NULL AUTO_INCREMENT,
  product_name VARCHAR(255),
  product_code VARCHAR(255),
  product_price FLOAT,
  product_quantity INT(11),
  PRIMARY KEY(id)
);