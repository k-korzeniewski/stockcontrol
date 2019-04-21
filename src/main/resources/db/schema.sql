CREATE TABLE IF NOT EXISTS stockcontrol.products(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  product_name VARCHAR(255),
  product_code VARCHAR(255),
  product_price FLOAT,
  product_quantity INT(11),
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS stockcontrol.roles(
  role_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  role VARCHAR(255),
  PRIMARY KEY(role_id)
);

CREATE TABLE IF NOT EXISTS stockcontrol.users(
  user_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(255),
  user_password VARCHAR(255),
  CONSTRAINT users UNIQUE(user_name),
  PRIMARY KEY(user_id)
);

CREATE TABLE IF NOT EXISTS stockcontrol.user_role(
  user_id BIGINT(20),
  role_id BIGINT(20),
  PRIMARY KEY(user_id,role_id),
  FOREIGN KEY(user_id) REFERENCES users(user_id),
  FOREIGN KEY(role_id) REFERENCES roles(role_id)
);

CREATE TABLE IF NOT EXISTS stockcontrol.token(
  id BIGINT(20) AUTO_INCREMENT,
  token VARCHAR(255),
  user_id BIGINT(20),
  PRIMARY KEY(id),
  CONSTRAINT token UNIQUE (token),
  FOREIGN KEY(user_id) REFERENCES users(user_id)
);