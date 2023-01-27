CREATE ROLE postgres WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  REPLICATION;

CREATE TABLE IF NOT EXISTS school.courses
(
    course_id SERIAL NOT NULL,
    course_name text COLLATE pg_catalog."default" NOT NULL,
    course_description text COLLATE pg_catalog."default",
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS school.courses
    OWNER to postgres;
    
CREATE TABLE IF NOT EXISTS school.groups
(
    group_id SERIAL NOT NULL,
    group_name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS school.groups
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS school.students
(
    student_id SERIAL NOT NULL,
    group_id integer REFERENCES school.groups (group_id),
    first_name text COLLATE pg_catalog."default" NOT NULL,
    last_name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS school.students
    OWNER to postgres;
    
CREATE TABLE IF NOT EXISTS school.students_courses
(
    student_id integer REFERENCES school.students (student_id) ON DELETE CASCADE NOT NULL,
    course_id integer REFERENCES school.courses (course_id) ON DELETE CASCADE NOT NULL,
    CONSTRAINT students_courses_pkey PRIMARY KEY (student_id, course_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS school.students_courses
    OWNER to postgres;