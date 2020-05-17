create table if not exists users
(
    id       int(10)      not null auto_increment,
    username varchar(20)  not null,
    password varchar(255) not null,
    primary key (id)
);

create table if not exists players
(
    account_id int(10)                 not null,
    id         int(10)                 not null auto_increment,
    username   varchar(20)             not null,
    exp        bigint(20)  default 0   not null,
    hair       smallint(6)             not null,
    eyes       smallint(6)             not null,
    str        smallint(6) default 0   not null,
    dex        smallint(6) default 0   not null,
    `int`      smallint(6) default 0   not null,
    luk        smallint(6) default 0   not null,
    vitality   smallint(6) default 0   not null,
    wisdom     smallint(6) default 0   not null,
    map_id     int(10)     default 301 not null,
    money      int(10)     default 0   not null,
    attraction int(10)     default 0   not null,
    level      tinyint(3)  default 1   not null,
    job        tinyint(3)  default 0   not null,
    gender     tinyint(3)              not null,
    primary key (id),
    constraint fk_players_users foreign key (account_id) references users (id) on delete cascade
);

create table if not exists storage
(
    player_id      int(10),
    item_id        smallint(6) not null,
    quantity       int(10)     not null,
    inventory_slot tinyint(3)  not null,
    constraint fk_storage_players foreign key (player_id) references players (id) on delete cascade
);
create index if not exists idx_storage_player_id on storage (player_id);
create unique index if not exists idx_users_username on users (username);