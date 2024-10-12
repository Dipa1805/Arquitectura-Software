CREATE DATABASE VentaCreCon;

USE VentaCreCon;

CREATE TABLE Cliente (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre NVARCHAR(100),
    ruc NVARCHAR(15)
);

CREATE TABLE Producto (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre NVARCHAR(50),
    precioUnitario FLOAT
);

CREATE TABLE Venta (
    id INT PRIMARY KEY IDENTITY(1,1),
    cliente_id INT,
    producto_id INT,
    cantidad INT,
    fecha DATE,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id),
    FOREIGN KEY (producto_id) REFERENCES Producto(id)
);

ALTER TABLE Venta
ADD subtotal FLOAT;
