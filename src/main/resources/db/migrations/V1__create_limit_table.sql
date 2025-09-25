create table if not exists users_limits
(
    id           bigserial primary key,
    user_id      bigint,
    daily_limit  decimal(20, 2),
    daily_usage  decimal(20, 2),
    creationDate timestamp,
    modifyDate   timestamp
);