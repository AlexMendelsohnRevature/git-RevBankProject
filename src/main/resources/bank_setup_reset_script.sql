drop table if exists "user";
drop table if exists "account";

create table "user"(
    id integer primary key autoincrement,
	username text,
	password text,
	loggedIn bit not null
);

create table "account"(
    id integer primary key autoincrement,
    username text,
    balance double
);

select * from "account" a join "user" u on u.id = a.id;