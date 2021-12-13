CREATE TABLE "Tipo_Atraccion" (
	"nombre"	TEXT NOT NULL,
	"active"	NUMERIC NOT NULL DEFAULT 0,
	PRIMARY KEY("nombre")
);

CREATE TABLE "Usuario" (
	"nombre"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"admin"	NUMERIC NOT NULL DEFAULT 0,
	"presupuesto"	INTEGER NOT NULL,
	"tiempo_disponible"	REAL NOT NULL,
	"tipo_atraccion"	TEXT NOT NULL,
	"active"	NUMERIC NOT NULL DEFAULT 0,
	FOREIGN KEY("tipo_atraccion") REFERENCES "Tipo_Atraccion"("nombre"),
	PRIMARY KEY("nombre")
);

CREATE TABLE "Atraccion" (
	"nombre"	TEXT NOT NULL,
	"costo"	INTEGER NOT NULL,
	"cupo"	INTEGER NOT NULL,
	"tiempo"	REAL NOT NULL,
	"tipo_atraccion"	TEXT NOT NULL,
	"active"	NUMERIC NOT NULL DEFAULT 0,
	FOREIGN KEY("tipo_atraccion") REFERENCES "Tipo_Atraccion"("nombre"),
	PRIMARY KEY("nombre")
);

CREATE TABLE "Promocion" (
	"nombre"	TEXT NOT NULL,
	"tipo_atraccion"	TEXT NOT NULL,
	"active"	NUMERIC NOT NULL DEFAULT 0,
	FOREIGN KEY("tipo_atraccion") REFERENCES "Tipo_Atraccion"("nombre"),
	PRIMARY KEY("nombre")
);

CREATE TABLE "Tiene_Atracciones" (
	"usuario"	TEXT NOT NULL,
	"atraccion"	TEXT NOT NULL,
	FOREIGN KEY("atraccion") REFERENCES "Atraccion"("nombre"),
	FOREIGN KEY("usuario") REFERENCES "Usuario"("nombre"),
	PRIMARY KEY("usuario","atraccion")
);

CREATE TABLE "Tiene_Promociones" (
	"usuario"	TEXT NOT NULL,
	"promocion"	TEXT NOT NULL,
	FOREIGN KEY("promocion") REFERENCES "Promocion"("nombre"),
	FOREIGN KEY("usuario") REFERENCES "Usuario"("nombre"),
	PRIMARY KEY("usuario","promocion")
);

CREATE TABLE "Lo_Puede_Contener" (
	"atraccion"	TEXT NOT NULL,
	"promocion"	TEXT NOT NULL,
	FOREIGN KEY("promocion") REFERENCES "Promocion"("nombre"),
	FOREIGN KEY("atraccion") REFERENCES "Atraccion"("nombre"),
	PRIMARY KEY("atraccion","promocion")
);

CREATE TABLE "Promocion_AxB" (
	"id"	INTEGER NOT NULL,
	"nombre"	TEXT NOT NULL UNIQUE,
	"atraccion_gratis"	TEXT NOT NULL,
	FOREIGN KEY("nombre") REFERENCES "Promocion"("nombre"),
	FOREIGN KEY("atraccion_gratis") REFERENCES "Atraccion"("nombre"),
	PRIMARY KEY("nombre")
);

CREATE TABLE "Promocion_Porcentual" (
	"id"	INTEGER NOT NULL,
	"nombre"	TEXT NOT NULL UNIQUE,
	"porcentaje_descuento"	REAL NOT NULL,
	FOREIGN KEY("nombre") REFERENCES "Promocion"("nombre"),
	PRIMARY KEY("nombre")
);

CREATE TABLE "Promocion_Absoluta" (
	"id"	INTEGER NOT NULL,
	"nombre"	TEXT NOT NULL UNIQUE,
	"costo_final"	INTEGER NOT NULL,
	FOREIGN KEY("nombre") REFERENCES "Promocion"("nombre"),
	PRIMARY KEY("nombre")
);