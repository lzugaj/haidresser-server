insert into "ROLE" values (1, 'USER', 'The user manager can perform CRUD operations over his/her profile and indents.');
insert into "ROLE" values (2, 'EMPLOYER', 'The role employer can perform specific CRUD operations on application users and indents.');
insert into "ROLE" values (3, 'MANAGER', 'The role manager can perform CRUD operations on all application users and indents.');

insert into "USER" values (1, 'Luka', 'Žugaj', 'lzugaj', 'password', 'luka.zugaj@gmail.com', '+385916017879', 0, true, false, 3);

insert into "ACCOMMODATION" values (1, 'Šišanje', 'Šišanje sa škarama i/ili mašinicom', 30, 20);
insert into "ACCOMMODATION" values (2, 'Uređivanje brade', 'Uređivanje brade sa škarama, mašinicom, žiletom', 20, 15);
insert into "ACCOMMODATION" values (3, 'Brijanje', 'Bijanje brade', 15, 10);
insert into "ACCOMMODATION" values (4, 'Farbanje', 'Farbanje kose sa bojom po želji', 45, 30);

insert into "INDENT" values (1, '2020-12-10', '11:20', '11:35', true, 1);

insert into "INDENT_ACCOMMODATION" values (1, 1);