create table if not exists persons (
    id      SERIAL primary key,
    name    varchar(75) not null ,
    gender  varchar(1) not null,
    age     int not null ,
    identification varchar(13) unique not null,
    direction varchar(80) not null,
    phone varchar(10) not null
    );

create table if not exists clients (
    id      SERIAL primary key,
    password    varchar(100) not null ,
    status  boolean default true,
    person_id     int not null
    );