CREATE TABLE Todo (
	id serial not null primary key,
	description text not null
);

insert into Todo (description) values ('So many things to do, will first creat a TODO app!');
insert into Todo (description) values ('Second thing is to drink a glass of water.');
