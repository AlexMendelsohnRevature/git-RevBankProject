drop table if exists "user";
drop table if exists "account";

create table "user"(
	username text,
	password text,
	loggedIn bit not null
);

create table "account"(
    username text,
    password text,
    balance double
);

insert into "user" values ('admin', 'password', false);

select * from "account" a join "user" u on u.username = a.username;