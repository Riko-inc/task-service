-- liquibase formatted sql
-- changeset Riko:initial_migrations

CREATE SEQUENCE IF NOT EXISTS "task-service".comments_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS "task-service".tasks_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS "task-service".users_sequence START WITH 1 INCREMENT BY 50;

CREATE TABLE "task-service".comments
(
    comment_id     BIGINT                      NOT NULL PRIMARY KEY ,
    author_id      BIGINT,
    content        VARCHAR(255)                NOT NULL,
    parent_task_id BIGINT                      NOT NULL,
    created_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
);

CREATE TABLE "task-service".tasks
(
    task_id             BIGINT                      NOT NULL PRIMARY KEY ,
    title               VARCHAR(255)                NOT NULL,
    description         VARCHAR(255)                NOT NULL,
    due_to              TIMESTAMP WITHOUT TIME ZONE,
    status              VARCHAR(255),
    priority            VARCHAR(255)                NOT NULL,
    created_by_user_id  BIGINT,
    assigned_to_user_id BIGINT,
    created_date        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
);

CREATE TABLE "task-service".users
(
    user_id                BIGINT                      NOT NULL PRIMARY KEY ,
    password               VARCHAR(255)                NOT NULL,
    email                  VARCHAR(255)                NOT NULL UNIQUE ,
    is_active              BOOLEAN                     NOT NULL,
    role                   VARCHAR(255)                NOT NULL,
    registration_date_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
);

ALTER TABLE "task-service".comments
    ADD CONSTRAINT FK_COMMENTS_ON_AUTHOR FOREIGN KEY (author_id)
        REFERENCES "task-service".users (user_id)
        ON DELETE SET NULL;

ALTER TABLE "task-service".comments
    ADD CONSTRAINT FK_COMMENTS_ON_PARENT_TASK FOREIGN KEY (parent_task_id)
        REFERENCES "task-service".tasks (task_id)
        ON DELETE CASCADE;