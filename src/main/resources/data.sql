-- Cursos
INSERT INTO curso (id, nombre, siglas, created_at)
VALUES (NEXTVAL('hibernate_sequence'), 'Desarrollo de Aplicaciones Multiplataforma', 'DAM', NOW());
INSERT INTO curso (id, nombre, siglas, created_at)
VALUES (NEXTVAL('hibernate_sequence'), 'Desarrollo de Aplicaciones Web', 'DAW', NOW());
-- Módulos
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
-- Alumnos
INSERT INTO alumno (id, nombre, correo, created_at, imagen)
VALUES (NEXTVAL('hibernate_sequence'), 'Sebastian', 'sebs@prueba.com', NOW(), 'https://api.lorem.space/image/face?w=150&h=150');
INSERT INTO alumno (id, nombre, correo, created_at, imagen)
VALUES (NEXTVAL('hibernate_sequence'), 'David', 'dav@prueba.com', NOW(), 'https://api.lorem.space/image/face?w=150&h=150');
INSERT INTO alumno (id, nombre, correo, created_at, imagen)
VALUES (NEXTVAL('hibernate_sequence'), 'Sandra', 'san@prueba.com', NOW(), 'https://api.lorem.space/image/face?w=150&h=150');
INSERT INTO alumno (id, nombre, correo, created_at, imagen)
VALUES (NEXTVAL('hibernate_sequence'), 'Alfredo', 'alf@prueba.com', NOW(), 'https://api.lorem.space/image/face?w=150&h=150');
INSERT INTO alumno (id, nombre, correo, created_at, imagen)
VALUES (NEXTVAL('hibernate_sequence'), 'Alejandro', 'ale@prueba.com', NOW(), 'https://api.lorem.space/image/face?w=150&h=150');
INSERT INTO alumno (id, nombre, correo, created_at, imagen)
VALUES (NEXTVAL('hibernate_sequence'), 'Celine', 'cel@prueba.com', NOW(), 'https://api.lorem.space/image/face?w=150&h=150');
-- Notas
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 5, 16, 3);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 6, 16, 4);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 7, 17, 5);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 8, 17, 6);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 9, 18, 7);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 7, 18, 8);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 6, 19, 9);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 8, 19, 10);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 6, 20, 11);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 5, 20, 12);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 7, 21, 13);
INSERT INTO calificacion (id, nota, id_alumno, id_modulo)
VALUES (NEXTVAL('hibernate_sequence'), 5, 21, 14);
-- Usuarios
-- Usuario USER-ADMIN -> Contraseña: Admin1
insert into usuarios (id, full_name, email, username, password, avatar, created_at, last_password_change_at)
values (1, 'Admin admin', 'admin@prueba.net', 'admin', '$2a$10$vPaqZvZkz6jhb7U7k/V/v.5vprfNdOnh4sxi/qpPRkYTzPmFlI9p2',
        'https://api.lorem.space/image/face?w=150&h=150', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into usuario_roles (usuario_id, roles)
values (1, 'USER');
insert into usuario_roles (usuario_id, roles)
values (1, 'ADMIN');
-- Usuario USER -> Contraseña: Marialopez1
insert into usuarios (id, full_name, email, username, password, avatar, created_at, last_password_change_at)
values (2, 'María López', 'maria.lopez@prueba.net', 'marialopez',
        '$2a$10$3i95KIxdl8igcpDby.URMOgwdDR2q9UaSfor2kJJrhAPfNOC/HMSi',
        'https://api.lorem.space/image/face?w=150&h=150', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into usuario_roles (usuario_id, roles)
values (2, 'USER');