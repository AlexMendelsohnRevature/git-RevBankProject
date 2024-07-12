drop table if exists "user";
drop table if exists "account";

create table "user"(
    id int,
	username text,
	password text,
	loggedIn bit not null
);

create table "account"(
    id int,
    username text,
    balance double
);

select * from "account" a join "user" u on u.id = a.id;