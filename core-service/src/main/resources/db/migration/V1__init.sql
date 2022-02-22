create table category
(
    id         bigserial primary key,
    title     varchar(50) not null unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into category(title)
values ('Food'),
       ('Clothes'),
       ('Shoes'),
       ('Books');

create table products
(
    id          bigserial primary key,
    title       varchar(255),
    category_id bigint not null references category (id),
    price       numeric(8,2),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);


insert into products (title, category_id, price)
values ('Milk', 1, 100.00),
       ('Bread', 1, 80.00),
       ('Cheese', 1, 90.00),
       ('Product1', 2, 810.00),
       ('Product2', 3, 20.00),
       ('Product3', 2, 830.00),
       ('Product4', 3, 840.00),
       ('Product5', 4, 850.00),
       ('Product6', 2, 880.00),
       ('Product7', 4, 220.00),
       ('Product8', 1, 380.00);

create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price numeric(8,2)    not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product numeric(8,2)    not null,
    price             numeric(8,2)    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

insert into orders (username, total_price, address, phone)
values (1, 200.00, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
values (1, 1, 2, 100.00, 200.00);









