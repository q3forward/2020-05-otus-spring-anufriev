create table authors(
    id bigint auto_increment,
    name varchar(50),
    primary key (id)
);

create table genres(
    id bigint auto_increment,
    name varchar(50),
    primary key (id)
);

create table books(
    id bigint auto_increment,
    title varchar(50),
    genre_id bigint references genres (id),
    author_id bigint references authors (id),
    primary key (id)
);