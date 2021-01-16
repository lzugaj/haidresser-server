drop table if exists "ROLE" cascade;
drop table if exists "USER" cascade;
drop table if exists "EMPLOYER" cascade;
drop table if exists "USER_ROLE" cascade;
drop table if exists "EMPLOYER_ROLE" cascade;
drop table if exists "ACCOMMODATION" cascade;
drop table if exists "INDENT" cascade;
drop table if exists "INDENT_ACCOMMODATION" cascade;

create table "ROLE" (
    id bigserial not null,
    name varchar(10) not null,
    description varchar(100),
    primary key (id)
);

create table "USER" (
    id bigserial not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    username varchar(20) not null,
    password varchar(10) not null,
    email varchar(100) not null,
    phone_number varchar(13) not null,
    number_of_reservations int,
    status varchar(20),
    has_reserved_indent boolean,
    primary key (id)
);

create table "EMPLOYER" (
    id bigserial not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    username varchar(20) not null,
    password varchar(10) not null,
    email varchar(100) not null,
    phone_number varchar(13) not null,
    primary key (id)
);

create table "USER_ROLE" (
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint user_role_fk foreign key (user_id) references "USER" (id),
    constraint role_user_fk foreign key (role_id) references "ROLE" (id)
);

create table "EMPLOYER_ROLE" (
     employer_id int not null,
     role_id int not null,
     primary key (employer_id, role_id),
     constraint employer_role_fk foreign key (employer_id) references "EMPLOYER" (id),
     constraint role_employer_fk foreign key (role_id) references "ROLE" (id)
);

create table "ACCOMMODATION" (
    id bigserial not null,
    name varchar(100) not null,
    description varchar(200) not null,
    price int not null,
    duration int not null,
    primary key (id)
);

create table "INDENT" (
    id bigserial not null,
    reservation_date date not null,
    reservation_time_from time not null,
    reservation_time_to time not null,
    is_active boolean,
    user_id int not null,
    primary key (id),
    constraint user_fk foreign key (user_id) references "USER" (id)
);

create table "INDENT_ACCOMMODATION" (
    indent_id int not null,
    accommodation_id int not null,
    primary key (indent_id, accommodation_id),
    constraint indent_fk foreign key (indent_id) references "INDENT" (id),
    constraint accommodation_fk foreign key (accommodation_id) references "ACCOMMODATION" (id)
);