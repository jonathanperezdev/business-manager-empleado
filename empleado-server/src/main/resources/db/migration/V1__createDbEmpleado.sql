CREATE TABLE CARGO(
	id serial primary key,
	nombre VARCHAR(30) not null UNIQUE,
	descripcion VARCHAR(300),
	funciones VARCHAR(300)
);

CREATE TABLE TIPO_DOCUMENTO(
  id serial primary key,
  nombre VARCHAR(40) NOT NULL UNIQUE,
  orden smallint NOT NULL
);

CREATE TABLE EMPLEADO (
  id bigserial primary key,
  tipo_documento INT NOT NULL,
  numero_documento VARCHAR(15) NOT NULL,
  nombres varchar(30) NOT NULL,
  apellidos varchar(30) NOT NULL,
  salario numeric(8) NOT NULL,
  direccion varchar(70) NOT NULL,
  numero_celular varchar(13),
  telefono varchar(13),
  contacto_emergencia_nombres varchar(30),
  contacto_emergencia_apellidos varchar(30),
  contacto_emergencia_telefono varchar(13),
  cargo INT NOT NULL,
  ubicacion INT,
  UNIQUE(tipo_documento, numero_documento),
  CONSTRAINT EMPLEADO_cargo_CARGO_id FOREIGN KEY (cargo) REFERENCES cargo(id),
  CONSTRAINT EMPLEADO_tipodoc_TIPO_DOCUMENTO_id FOREIGN KEY (tipo_documento) REFERENCES TIPO_DOCUMENTO(id)
) ;

CREATE TABLE UBICACION (
  id serial primary key,
  nombre varchar(30) NOT NULL UNIQUE,
  direccion varchar(70) NOT NULL,
  tipo varchar(15) NOT NULL,
  descripcion varchar(300),
  oficialACargo BIGINT,
  ingenieroACargo BIGINT,
  CONSTRAINT UBICACION_ingenieroACargo_EMPLEADO_id FOREIGN KEY (ingenieroACargo) REFERENCES empleado (id),
  CONSTRAINT UBICACION_oficialACargo_EMPLEADO_id FOREIGN KEY (oficialACargo) REFERENCES empleado (id)
) ;

ALTER TABLE EMPLEADO
ADD CONSTRAINT EMPLEADO_ubicacion_UBICACION_id FOREIGN KEY (ubicacion) REFERENCES UBICACION (id);