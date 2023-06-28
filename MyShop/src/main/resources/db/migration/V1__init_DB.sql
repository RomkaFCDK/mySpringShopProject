-- USERS
DROP SEQUENCE IF EXISTS user_seq;
create sequence user_seq start 1 increment 1;

DROP TABLE IF EXISTS users CASCADE;
create table users
(
    id          int    not null,
    name        varchar(255),
    password    varchar(255),
    email       varchar(255),
    role        varchar(255),
    primary key (id)
);

-- BASKET
DROP SEQUENCE IF EXISTS basket_seq;
create sequence basket_seq start 1 increment 1;

DROP TABLE IF EXISTS baskets CASCADE;
create table baskets
(
    id      int not null,
    user_id int,
    primary key (id)
);

-- LINK BETWEEN BASKET AND USER
alter table if exists baskets
    add constraint baskets_fk_user
        foreign key (user_id) references users;

-- CATEGORY
DROP SEQUENCE IF EXISTS category_seq;
create sequence category_seq start 1 increment 1;

DROP TABLE IF EXISTS categories CASCADE;
create table categories
(
    id      int not null,
    title   varchar(255),
    primary key (id)
);

-- PRODUCT
DROP SEQUENCE IF EXISTS product_seq;
create sequence product_seq start 1 increment 1;

DROP TABLE IF EXISTS products CASCADE;
create table products
(
    id      int not null ,
    price   numeric(19, 2),
    title   varchar(255),
    primary key (id)
);

-- CATEGORY AND PRODUCT
DROP TABLE IF EXISTS products_categories CASCADE;
create table products_categories
(
    product_id  int not null,
    category_id int not null
);

alter table if exists products_categories
    add constraint products_categories_fk_category
        foreign key (category_id) references categories;

alter table if exists products_categories
    add constraint products_categories_fk_products
        foreign key (product_id) references products;

-- PRODUCT IN BASKET
DROP TABLE IF EXISTS BASKETS_PRODUCTS CASCADE;
create table baskets_products
(
    basket_id   int not null ,
    product_id  int not null
);

alter table if exists baskets_products
    add constraint baskets_products_fk_products
        foreign key (product_id) references products;

alter table if exists baskets_products
    add constraint baskets_products_fk_basket
        foreign key (basket_id) references baskets;

-- ORDERS
DROP SEQUENCE IF EXISTS order_seq;
create sequence order_seq start 1 increment 1;

DROP TABLE IF EXISTS orders CASCADE;
create table orders
(
    id      int not null,
    address varchar(255),
    updated timestamp,
    created timestamp,
    status  varchar(255),
    sum     numeric(19, 2),
    user_id int,
    primary key (id)
);

alter table if exists orders
    add constraint orders_fk_user
        foreign key (user_id) references users;

-- ORDER DETAILS
DROP SEQUENCE IF EXISTS order_details_seq;
create sequence order_details_seq start 1 increment 1;

DROP TABLE IF EXISTS orders_details CASCADE;
create table orders_details
(
    id          int not null,
    amount      numeric(19, 2),
    price       numeric(19, 2),
    order_id    int,
    product_id  int,
    details_id  int not null,
    primary key (id)
);

alter table if exists orders_details
    add constraint orders_details_fk_order
            foreign key (order_id) references orders;

alter table if exists orders_details
    add constraint orders_details_fk_products
        foreign key (product_id) references products;