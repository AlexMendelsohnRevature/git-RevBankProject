drop table if exists "user";

create table "user"(
	username text,
	password text

);

insert into "user" values ('admin', 'password');