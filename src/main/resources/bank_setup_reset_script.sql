drop table if exists accounts;
drop table if exists users;

create table users(
    id INTEGER primary key autoincrement,
	username text,
	password text,
	loggedIn bit not null
);

create table accounts (
    id INTEGER primary key autoincrement,
    userid int,
    balance double,
    username text,
    foreign key (userid) references users (id)
);

select * from accounts a join users u on a.userid = u.id;