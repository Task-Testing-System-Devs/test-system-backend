-- This file contains script for consequent creation of all tables in test-system database.

CREATE TABLE users
(
    user_id     SERIAL PRIMARY KEY,
    role  varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    password text NOT NULL
);

CREATE TABLE contests
(
    contest_id    SERIAL PRIMARY KEY,
    ejudge_id     INTEGER      NOT NULL,
    title         varchar(250) NOT NULL,
    start_time    TIMESTAMP    NOT NULL,
    finish_time   TIMESTAMP    NOT NULL,
    difficulty    INTEGER      NOT NULL,
    is_resolvable BOOLEAN      NOT NULL,
    is_mark_rated BOOLEAN      NOT NULL,
    is_task_rated BOOLEAN      NOT NULL
);

CREATE TABLE classifications
(
    classification_id SERIAL PRIMARY KEY,
    title             varchar(250) NOT NULL
);

CREATE TABLE tasks
(
    task_id           SERIAL PRIMARY KEY,
    ejudge_id         INTEGER      NULL,
    title             varchar(250) NOT NULL,
    description       TEXT         NOT NULL,
    memory_limit      DECIMAL      NOT NULL,
    time_limit        DECIMAL      NOT NULL,
    attempts_amount   SMALLINT     NOT NULL,
    classification_id INTEGER REFERENCES classifications(classification_id),
    difficulty        DECIMAL      NOT NULL,
    is_private        BOOLEAN      NOT NULL,
    grade             DECIMAL      NOT NULL
);

CREATE TABLE unique_contest_tasks
(
    unique_task_id SERIAL PRIMARY KEY,
    contest_id     INTEGER REFERENCES contests(contest_id),
    task_id        INTEGER REFERENCES tasks(task_id),
    weight         DECIMAL NOT NULL
);

CREATE TABLE languages
(
    language_id SERIAL PRIMARY KEY,
    title       varchar(25) NOT NULL
);

CREATE TABLE status
(
    status_id SERIAL PRIMARY KEY,
    title     varchar(25) NOT NULL
);

CREATE TABLE solutions
(
    solution_id     SERIAL PRIMARY KEY,
    unique_task_id  INTEGER REFERENCES unique_contest_tasks(unique_task_id),
    code            TEXT NOT NULL,
    language_id     INTEGER REFERENCES languages(language_id),
    status_id       INTEGER REFERENCES status(status_id),
    used_time       DECIMAL NOT NULL,
    used_memory     DECIMAL NOT NULL,
    error_test      varchar(10) NOT NULL
);

CREATE TABLE users_info
(
    user_info_id SERIAL PRIMARY KEY,
    user_id     INTEGER REFERENCES users(user_id),
    first_name  varchar(50) NOT NULL,
    last_name   varchar(50) NOT NULL,
    middle_name varchar(50) NULL
);

CREATE TABLE user_solutions
(
    user_solutions_id SERIAL PRIMARY KEY,
    user_id     INTEGER REFERENCES users(user_id),
    solution_id INTEGER REFERENCES solutions(solution_id)
);

CREATE TABLE departments
(
    department_id SERIAL PRIMARY KEY,
    title         text NOT NULL
);

CREATE TABLE departments_conn
(
    departments_conn_id SERIAL PRIMARY KEY,
    department_id INTEGER REFERENCES departments(department_id),
    user_id         INTEGER REFERENCES users(user_id)
);

CREATE TABLE ed_groups
(
    ed_group_id SERIAL PRIMARY KEY,
    title varchar(50) NOT NULL
);

CREATE TABLE ed_groups_conn
(
    ed_groups_conn_id SERIAL PRIMARY KEY,
    ed_group_id INTEGER REFERENCES ed_groups(ed_group_id),
    department_id         INTEGER REFERENCES departments(department_id)
);

CREATE TABLE created_tasks
(
    created_tasks_id SERIAL PRIMARY KEY,
    task_id INTEGER REFERENCES tasks(task_id),
    user_id INTEGER REFERENCES users(user_id)
);

CREATE TABLE created_contests
(
    created_contests_id SERIAL PRIMARY KEY,
    contest_id  INTEGER REFERENCES contests(contest_id),
    user_id     INTEGER REFERENCES users(user_id)
);

CREATE TABLE users_on_contest
(
    users_on_contest_id SERIAL PRIMARY KEY,
    contest_id  INTEGER REFERENCES contests(contest_id),
    user_id     INTEGER REFERENCES users(user_id)
);

CREATE TABLE tasks_of_contest
(
    tasks_of_contest_id SERIAL PRIMARY KEY,
    contest_id  INTEGER REFERENCES contests(contest_id),
    task_id     INTEGER REFERENCES tasks(task_id)
);

CREATE TABLE available_languages
(
    available_languages_id SERIAL PRIMARY KEY,
    contest_id  INTEGER REFERENCES contests(contest_id),
    language_id INTEGER REFERENCES languages(language_id)
);

CREATE TABLE tests_info
(
    test_id     SERIAL PRIMARY KEY,
    input       TEXT    NOT NULL,
    output      TEXT    NOT NULL,
    is_visible  BOOLEAN NOT NULL
);

CREATE TABLE task_tests
(
    task_tests_id SERIAL PRIMARY KEY,
    task_id INTEGER REFERENCES tasks(task_id),
    test_id INTEGER REFERENCES tests_info(test_id)
);
