create table mobile
(
    id           bigserial    not null
        constraint mobile_pkey
            primary key,
    model        varchar(100) not null,
    price        integer      not null,
    manufacturer varchar(100) not null
);

alter table mobile
    owner to postgres;

create table users
(
    user_id     serial      not null
        constraint users_pk
            primary key,
    user_name   varchar(10) not null,
    user_pass   varchar(10),
    user_prompt text
);

alter table users
    owner to postgres;

create unique index users_user_id_uindex
    on users (user_id);

create unique index users_user_name_uindex
    on users (user_name);


