DROP TABLE IF EXISTS superheros;

CREATE TABLE superheros (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  power VARCHAR(50) NOT NULL
);
