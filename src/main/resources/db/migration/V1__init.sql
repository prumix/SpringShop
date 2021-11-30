DROP TABLE IF EXISTS products CASCADE;

CREATE TABLE products
(
    id    bigserial primary key,
    title VARCHAR(255),
    cost  INTEGER
);

insert into products(title, cost)
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