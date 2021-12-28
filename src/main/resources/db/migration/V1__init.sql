DROP TABLE IF EXISTS products CASCADE;

CREATE TABLE products
(
    id    bigserial primary key,
    title VARCHAR(255),
    price  INTEGER
);

insert into products(title, price)
values ('product',10),
       ('product1',11),
       ('product2',12),
       ('product3',13),
       ('product4',14),
       ('product5',15),
       ('product6',16),
       ('product7',17),
       ('product8',18),
       ('product9',19),
       ('product10',20),
       ('product11',21),
       ('product12',22),
       ('product13',23),
       ('product14',24),
       ('product15',25),
       ('product16',26),
       ('product17',27),
       ('product18',28),
       ('product19',29),
       ('product20',30);

create table users (
                       id         bigserial primary key,
                       username   varchar(36) not null,
                       password   varchar(80) not null,
                       email      varchar(50) unique,
                       created_at timestamp default current_timestamp,
                       updated_at timestamp default current_timestamp
);

create table roles (
                       id         bigserial primary key,
                       name       varchar(50) not null,
                       created_at timestamp default current_timestamp,
                       updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles (
                             user_id bigint not null references users (id),
                             role_id bigint not null references roles (id),
                             primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);


create table orders (
                        id              bigserial primary key,
                        user_id         bigint not null references users (id),
                        total_price     int not null,
                        address         varchar(255),
                        phone           varchar(255)
);

create table order_items (
                             id                      bigserial primary key,
                             product_id              bigint not null references products (id),
                             user_id                 bigint not null references users (id),
                             order_id                bigint not null references orders (id),
                             quantity                int not null,
                             price_per_product       int not null,
                             price                   int not null
);