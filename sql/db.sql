CREATE DATABASE panaderia;

CREATE TABLE articulos(id INT,
nombre VARCHAR(45), 
tipo VARCHAR(15), 
precio FLOAT DEFAULT 0,
PRIMARY KEY(id));

CREATE TABLE ventas(id INT,
monto FLOAT,
PRIMARY KEY(id));

CREATE TABLE articulos_ventas(id INT AUTO_INCREMENT,
venta_id INT,
articulo_id INT,
cantidad_articulo INT,
monto_articulo FLOAT,
PRIMARY KEY(id));

CREATE TABLE movimientos(id INT AUTO_INCREMENT,
descripcion VARCHAR(120),
tipo VARCHAR(20),
monto FLOAT,
usuario_id INT,
PRIMARY KEY(id));

CREATE TABLE usuarios(id INT AUTO_INCREMENT,
username VARCHAR(20),
nyap VARCHAR(50),
pass VARCHAR(20),
PRIMARY KEY(id));

CREATE TABLE empleados(id INT AUTO_INCREMENT,
nyap VARCHAR(50),
PRIMARY KEY(id));