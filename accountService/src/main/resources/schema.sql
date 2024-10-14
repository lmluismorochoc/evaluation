create table if not exists accounts (
    id                  SERIAL primary key,
    account_number      varchar(25) not null unique,
    account_type        varchar(15) not null,
    initial_balance     DECIMAL not null ,
    status              boolean default true,
    client_id           int not null
    );

create table if not exists movements (
    id                  SERIAL primary key,
    movement_type       varchar(10) not null ,
    balance             DECIMAL  NOT NULL,
    amount              DECIMAL  not null,
    date                DATE DEFAULT CURRENT_DATE,
    account_id          int not null
    );