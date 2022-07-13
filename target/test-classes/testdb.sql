drop table if exists users;

create table users (
	id serial primary key,
	first_name varchar,
	last_name varchar,
	username varchar,
	pass varchar
);