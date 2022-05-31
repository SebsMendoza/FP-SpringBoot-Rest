INSERT INTO curso (id, nombre, siglas, created_at)
VALUES (NEXTVAL('hibernate_sequence'), 'Desarrollo de Aplicaciones Multiplataforma', 'DAM', NOW());
INSERT INTO curso (id, nombre, siglas, created_at)
VALUES (NEXTVAL('hibernate_sequence'), 'Desarrollo de Aplicaciones Web', 'DAW', NOW());
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 1, 'Base de Datos', 'BBDD', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 1, 'Entornos de Desarrollo', 'ED', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 1, 'Formación y Orientación Laboral', 'FOL', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 1, 'Lenguaje de Marcas y Sistemas de Gestión de Información', 'LMSGI', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 1, 'Programación', 'P', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 1, 'Sistemas Informáticos', 'SI', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 2, 'Acceso a Datos', 'AD', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 2, 'Desarrollo de Interfaces', 'DI', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 2, 'Empresa e Iniciativa Emprendedora', 'EIE', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 2, 'Inglés Ténico', 'IT', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 2, 'Programación de Servicios y Procesos', 'PSP', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 2, 'Sistemas de Gestión Empresarial', 'SGE', NOW(), 1);
INSERT INTO modulo (id, anio, nombre, siglas, created_at, id_curso)
VALUES (NEXTVAL('hibernate_sequence'), 2, 'Programación Multimedia y Dispositivos Móviles', 'PMDM', NOW(), 1);
INSERT INTO alumno (id, nombre, correo, created_at)
VALUES (NEXTVAL('hibernate_sequence'), 'Sebastian', 'sebs@prueba.com', NOW());
INSERT INTO alumno (id, nombre, correo, created_at)
VALUES (NEXTVAL('hibernate_sequence'), 'David', 'dav@prueba.com', NOW());
INSERT INTO alumno (id, nombre, correo, created_at)
VALUES (NEXTVAL('hibernate_sequence'), 'Sandra', 'san@prueba.com', NOW());
INSERT INTO alumno (id, nombre, correo, created_at)
VALUES (NEXTVAL('hibernate_sequence'), 'Alfredo', 'alf@prueba', NOW());