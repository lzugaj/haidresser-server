drop table if exists "ROLE" cascade ;
drop table if exists "USER" cascade;
drop table if exists "SERVICE" cascade;
drop table if exists "ORDERS" cascade;
drop table if exists "ORDER_SERVICE" cascade;

create table "ROLE" (
    id int not null,
    name varchar(10) not null,
    description varchar(100),
    primary key (id)
);

create table "USER" (
    id int not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    username varchar(20) not null,
    password varchar(10) not null,
    email varchar(100) not null,
    phone_number varchar(13) not null,
    number_of_reservations int,
    is_active boolean,
    role_id int not null,
    primary key (id),
    constraint role_fk foreign key (role_id) references "ROLE" (id)
);

create table "SERVICE" (
    id int not null,
    name varchar(100) not null,
    description varchar(200) not null,
    price decimal(5, 2) not null,
    duration int not null,
    primary key (id)
);

create table "ORDERS" (
    id int not null,
    reservation_date date not null,
    reservation_time time not null,
    canceled_reservation_date date,
    canceled_reservation_time time,
    user_id int not null,
    primary key (id),
    constraint user_fk foreign key (user_id) references "USER" (id)
);

create table "ORDER_SERVICE" (
    order_id int not null,
    service_id int not null,
    primary key (order_id, service_id),
    constraint order_fk foreign key (order_id) references "ORDERS" (id),
    constraint service_fk foreign key (service_id) references "SERVICE" (id)
);