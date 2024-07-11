drop table if exists "user";

create table "user"(
	username text,
	password text

);

create table "account"(
    username text,
    balance double
)

insert into "user" values ('admin', 'password');

join "user" on "user".username = "account".username;