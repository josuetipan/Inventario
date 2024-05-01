drop table if exists categorias;
create table categorias (
	codigo_cat serial not null,
	nombre varchar(100) not null,
	categoria_padre int ,
	constraint categorias_pk primary key (codigo_cat),
	constraint categorias_fk foreign key (categoria_padre)
	references categorias(codigo_cat)
);
insert into categorias (nombre,categoria_padre)
values('Materia prima',null);
insert into categorias (nombre,categoria_padre)
values('Proteina',1);
insert into categorias (nombre,categoria_padre)
values('Salsa',1);
insert into categorias (nombre,categoria_padre)
values('Punto de venta',null);
insert into categorias (nombre,categoria_padre)
values('Bebidas',4);
insert into categorias (nombre,categoria_padre)
values('Con alcohol',5);
insert into categorias (nombre,categoria_padre)
values('Sin alcohol',5);
----------------------------------------------
drop table if exists categorias_unidad_medida;
create table categorias_unidad_medida(
	codigo_cudm char(1) not null,
	nombre varchar(100) not null,
	constraint categorias_unidad_medida_pk primary key (codigo_cudm)
);
insert into categorias_unidad_medida(codigo_cudm,nombre)
values('U','Unidades');
insert into categorias_unidad_medida(codigo_cudm,nombre)
values('V','Volumen');
insert into categorias_unidad_medida(codigo_cudm,nombre)
values('P','Peso');
-----------------------------------
drop table if exists unidades_medida;
create table unidades_medida(
	codigo_udm varchar(5) not null,
	descripcion varchar(100) not null,
	categoria_udm char(1) not null,
	constraint unidades_medida_pk primary key (codigo_udm),
	constraint unidades_medida_fk foreign key (categoria_udm) references categorias_unidad_medida(codigo_cudm)
);
insert into unidades_medida (codigo_udm,descripcion,categoria_udm)
values('ml','mililiros','V');
insert into unidades_medida (codigo_udm,descripcion,categoria_udm)
values('l','litros','V');
insert into unidades_medida (codigo_udm,descripcion,categoria_udm)
values('u','unidada','U');
insert into unidades_medida (codigo_udm,descripcion,categoria_udm)
values('d','docena','U');
insert into unidades_medida (codigo_udm,descripcion,categoria_udm)
values('g','gramos','P');
insert into unidades_medida (codigo_udm,descripcion,categoria_udm)
values('kg','kilogramos','P');
insert into unidades_medida (codigo_udm,descripcion,categoria_udm)
values('lb','libras','P');
-----------------------------------------------
drop table if exists productos;
create table productos(
	codigo_pro serial not null,
	nombre varchar(100) not null,
	UDM varchar(5) not null,
	precio_venta money,
	tiene_iva boolean,
	coste money,
	categoria int not null,
	stock int,
	constraint productos_pk primary key (codigo_pro),
	constraint producto_unidades_fk foreign key (UDM) references unidades_medida(codigo_udm),
	constraint producto_categoria_fk foreign key (categoria) references categorias(codigo_cat)
);
insert into productos(nombre,UDM,precio_venta,tiene_iva,coste,categoria,stock)
values('Coca cola pequeña','u',9.5804,true,0.3729,7,105);
insert into productos(nombre,UDM,precio_venta,tiene_iva,coste,categoria,stock)
values('Salsa de tomate','kg',0.95,true,0.8736,3,0);
insert into productos(nombre,UDM,precio_venta,tiene_iva,coste,categoria,stock)
values('Mostaza','kg',9.5,true,0.89,3,0);
insert into productos(nombre,UDM,precio_venta,tiene_iva,coste,categoria,stock)
values('Fuze Tea','u',0.8,true,0.70,7,49);
-------------------------------------------------
drop table if exists tipo_documentos;--
create table tipo_documentos(
	codigo_td char(1) not null,
	descripcion varchar(100) not null,
	constraint tipo_documento_pk primary key (codigo_td)
);
insert into tipo_documentos(codigo_td,descripcion)
values ('C','Cedula');
insert into tipo_documentos(codigo_td,descripcion)
values ('R','Ruc');
------------------------------------------
drop table if exists proveedores;-----------
create table proveedores(
	identificador varchar(15),
	tipo_documento char(1),
	nombre varchar(100),
	telefono char(100),
	correo varchar(100),
	direccion varchar(100),
	constraint proveedores_pk primary key (identificador),
	constraint tipo_proveedor_fk foreign key (tipo_documento) references tipo_documentos(codigo_td)
);
insert into proveedores(identificador,tipo_documento,nombre,telefono,correo,direccion)
values('1792285747','C','Santiago Mosquera','0992920306','zantycb89@gmail.com','Cumbayork');
insert into proveedores(identificador,tipo_documento,nombre,telefono,correo,direccion)
values('1792285747001','R','Snacks SA','0992920398','snacks@gmail.com','La tola');
---------------------------------------------
drop table if exists cabecera_pedido;----------
create table cabecera_pedido(
	numero serial not null,
	proveedor char(15) not null,
	fecha date,
	estado char(1),
	constraint cabecera_pk primary key (numero),
	constraint cabecera_pro_fk foreign key (proveedor) references proveedores(identificador)
	--constraint estado_pedido_pk foreign key (estado) references estado_pedido(codigo)
);
insert into cabecera_pedido(proveedor,fecha,estado)
values ('1792285747','30/11/2023','R');
insert into cabecera_pedido(proveedor,fecha,estado)
values ('1792285747','30/11/2023','S');
-------------------------------------------------
drop table if exists detalle_pedido;----------
create table detalle_pedido(
	codigo serial not null,
	cabecera_pedido int,
	producto int,
	cantidad_solicitada int,
	subtotal money,
	cantidad_recibida int,
	constraint detalle_pe_pk primary key (codigo),
	constraint cabecera_fk foreign key (cabecera_pedido) references cabecera_pedido(numero),
	constraint producto1_fk foreign key (producto) references productos(codigo_pro)
);
insert into detalle_pedido (cabecera_pedido,producto,cantidad_solicitada,subtotal,cantidad_recibida)
values(1,1,100,37.29,100);
insert into detalle_pedido (cabecera_pedido,producto,cantidad_solicitada,subtotal,cantidad_recibida)
values(1,4,50,11.8,50);
insert into detalle_pedido (cabecera_pedido,producto,cantidad_solicitada,subtotal,cantidad_recibida)
values(2,1,10,3.73,10);
--------------------------------
drop table if exists estado_pedido;
create table estado_pedido(
	codigo char(1) not null,
	descripcion varchar(100),
	constraint estado_pk primary key (codigo)
);
insert into estado_pedido values('S','Solicitado'),
								('R','Recibido');
------------------------------------------
drop table if exists historial_stock;
create table historial_stock(
	codigo serial not null,
	fecha timestamp without time zone,
	referencia varchar(10),
	producto int,
	cantidad int,
	constraint historial_pk primary key (codigo),
	constraint producto_fk foreign key (producto) references productos(codigo_pro)
);
insert into historial_stock (fecha,referencia,producto,cantidad)
values('20/11/2023 19:59','Pedido 1',1,100);
insert into historial_stock (fecha,referencia,producto,cantidad)
values('20/11/2023 19:59','Pedido 1',4,50);
insert into historial_stock (fecha,referencia,producto,cantidad)
values('20/11/2023 20:00','Pedido 2',1,10);
insert into historial_stock (fecha,referencia,producto,cantidad)
values('20/11/2023 20:00','Pedido 1',1,-5);
insert into historial_stock (fecha,referencia,producto,cantidad)
values('20/11/2023 20:00','Venta 1',4,-1);
-----------------------------------------
drop table if exists cabecera_ventas;
create table cabecera_ventas(
	codigo serial not null,
	fecha timestamp without time zone,
	total_sin_iva money,
	iva money,
	total money,
	constraint cabecera1_pk primary key (codigo)
);
insert into cabecera_ventas(fecha,total_sin_iva,iva,total)
values('20/11/2023 20:00',3.26,0.39,3.65);

drop table if exists detalle_venta;
create table detalle_venta(
	codigo serial not null,
	cabecera_ventas int not null,
	producto int not null,
	cantidad int ,
	precio_ventas money,
	subtotal money,
	subtotal_con_iva money,
	constraint detalle_pk primary key (codigo),
	constraint cabecera2_fk foreign key (cabecera_ventas) references cabecera_ventas(codigo),
	constraint producto_pk foreign key (producto) references productos(codigo_pro)
);
insert into detalle_venta(cabecera_ventas,producto,cantidad,precio_ventas,subtotal,subtotal_con_iva)
values(1,1,5,0.58,2.9,3.25);
insert into detalle_venta(cabecera_ventas,producto,cantidad,precio_ventas,subtotal,subtotal_con_iva)
values(1,4,1,0.36,0.36,0.4);

--select * from cabecera_ventas
--select * from detalle_venta
--select * from estado_pedido
--select * from cabecera_pedido
--select * from categorias_unidad_medida;
--select * from categorias
--select * from unidades_medida
--select * from detalle_pedido
--select * from historial_stock
--select * from productos
--select * from tipo_documentos
--select * from proveedores







