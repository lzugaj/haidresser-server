drop table if exists "ROLE" cascade;
drop table if exists "PERSON" cascade;
drop table if exists "PERSON_ROLE" cascade;
drop table if exists "USER" cascade;
drop table if exists "MANAGER" cascade;
drop table if exists "COUNTY" cascade;
drop table if exists "CITY" cascade;
drop table if exists "SALON" cascade;
drop table if exists "EMPLOYER" cascade;
drop table if exists "CERTIFICATE" cascade;
drop table if exists "PERSON_CERTIFICATE" cascade;
drop table if exists "ACCOMMODATION" cascade;
drop table if exists "SALON_ACCOMMODATION" cascade;
drop table if exists "INDENT" cascade;
drop table if exists "INDENT_ACCOMMODATION" cascade;

create table "ROLE" (
    id bigserial not null,
    name varchar(10),
    description varchar(100),
    primary key (id)
);

create table "PERSON" (
    id bigserial not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(50) not null,
    username varchar(50) not null,
    password varchar(50) not null,
    phone_number varchar(13) not null,
    gender varchar(10) not null,
    photo_file_path varchar(50),
    registered_at timestamp,
    primary key (id)
);

create table "PERSON_ROLE" (
    person_id int not null,
    role_id int not null,
    primary key (person_id, role_id),
    constraint fk_person_role foreign key (person_id) references "PERSON" (id),
    constraint fk_role_person foreign key (role_id) references "ROLE" (id)
);

create table "USER" (
    person_id bigint not null,
    number_of_reservations int,
    has_reserved_indent boolean,
    status varchar(20),
    primary key (person_id),
    constraint fk_user_person foreign key (person_id) references "PERSON" (id)
);

create table "MANAGER" (
    person_id bigint not null,
    oib varchar(11) not null,
    is_employer boolean not null,
    primary key (person_id),
    constraint fk_manager_person foreign key (person_id) references "PERSON" (id)
);

create table "COUNTY" (
    id bigserial not null,
    name varchar(50) not null,
    primary key (id)
);

create table "CITY" (
    id bigserial not null,
    name varchar(50) not null,
    short_name varchar(10) not null,
    postal_code varchar(10) not null,
    county_id int not null,
    primary key (id),
    constraint fk_city_county foreign key (county_id) references "COUNTY" (id)
);

create table "SALON" (
    id bigserial not null,
    name varchar(50) not null,
    oib varchar(11) not null,
    street_name varchar(50) not null,
    street_number varchar(10) not null,
    additional_street_number varchar(10),
    phone_number varchar(13) not null,
    status varchar(20),
    number_of_employees int,
    registered_at timestamp,
    manager_id int not null,
    county_id int not null,
    city_id int not null,
    primary key (id),
    constraint fk_salon_manager foreign key (manager_id) references "PERSON" (id),
    constraint fk_salon_county foreign key (county_id) references "COUNTY" (id),
    constraint fk_salon_city foreign key (city_id) references "CITY" (id)
);

create table "EMPLOYER" (
    person_id bigint not null,
    salon_id int not null,
    primary key (person_id),
    constraint fk_employer_salon foreign key (salon_id) references "SALON" (id)
);

create table "CERTIFICATE" (
    id bigserial not null,
    name varchar(100) not null,
    description varchar(512),
    receipt_on date,
    created_at timestamp,
    photo_file_path varchar(50),
    primary key (id)
);

create table "PERSON_CERTIFICATE" (
    person_id int not null,
    certificate_id int not null,
    primary key (person_id, certificate_id),
    constraint fk_person_certificate foreign key (person_id) references "PERSON" (id),
    constraint fk_certificate_person foreign key (certificate_id) references "CERTIFICATE" (id)
);

create table "ACCOMMODATION" (
    id bigserial not null,
    name varchar(100) not null,
    description varchar(200) not null,
    price int not null,
    duration int not null,
    primary key (id)
);

create table "SALON_ACCOMMODATION" (
    salon_id int not null,
    accommodation_id int not null,
    primary key (salon_id, accommodation_id),
    constraint fk_salon_accommodation foreign key (salon_id) references "SALON" (id),
    constraint fk_accommodation_salon foreign key (accommodation_id) references "ACCOMMODATION" (id)
);

create table "INDENT" (
    id bigserial not null,
    reserved_on date not null,
    reserved_from time not null,
    reserved_to time not null,
    status varchar(20),
    user_id int not null,
    employer_id int not null,
    salon_id int not null,
    primary key (id),
    constraint fk_indent_user foreign key (user_id) references "PERSON" (id),
    constraint fk_indent_employer foreign key (employer_id) references "PERSON" (id),
    constraint fk_indent_salon foreign key (salon_id) references "SALON" (id)
);

create table "INDENT_ACCOMMODATION" (
    indent_id int not null,
    accommodation_id int not null,
    primary key (indent_id, accommodation_id),
    constraint fk_indent_accommodation foreign key (indent_id) references "INDENT" (id),
    constraint fk_accommodation_indent foreign key (accommodation_id) references "ACCOMMODATION" (id)
);