-- insert static data into tables

insert into "ROLE" values (1, 'USER', 'The user manager can perform CRUD operations over his/her profile and indents.');
insert into "ROLE" values (2, 'EMPLOYER', 'The role employer can perform specific CRUD operations on application users and indents.');
insert into "ROLE" values (3, 'MANAGER', 'The role manager can perform CRUD operations on all application users and indents.');

insert into "COUNTY" values (1, 'Zagrebačka županija');
insert into "COUNTY" values (2, 'Krapinsko-zagorska županija');
insert into "COUNTY" values (3, 'Sisačko-moslavačka županija');
insert into "COUNTY" values (4, 'Karlovačka županija');
insert into "COUNTY" values (5, 'Varaždinska županija');
insert into "COUNTY" values (6, 'Koprivničko-križevačka županija');
insert into "COUNTY" values (7, 'Bjelovarsko-bilogorska županija');
insert into "COUNTY" values (8, 'Primorsko-goranska županija');
insert into "COUNTY" values (9, 'Ličko-senjska županija');
insert into "COUNTY" values (10, 'Virovitičko-podravska županija');
insert into "COUNTY" values (11, 'Požeško-slavonska županija');
insert into "COUNTY" values (12, 'Brodsko-posavska županija');
insert into "COUNTY" values (13, 'Zadarska županija');
insert into "COUNTY" values (14, 'Osječko-baranjska županija');
insert into "COUNTY" values (15, 'Šibensko-kninska županija');
insert into "COUNTY" values (16, 'Vukovarsko-srijemska županija');
insert into "COUNTY" values (17, 'Splitsko-dalmatinska županija');
insert into "COUNTY" values (18, 'Istarska županija');
insert into "COUNTY" values (19, 'Dubrovačko-neretvanska županija');
insert into "COUNTY" values (20, 'Međimurska županija');
insert into "COUNTY" values (21, 'Grad Zagreb');

insert into "CITY" values (1, 'Zagreb', 'ZG', '10000', 21);
insert into "CITY" values (2, 'Osijek', 'OS', '31000', 14);
insert into "CITY" values (3, 'Slavonski Brod', 'SB', '35000', 12);
insert into "CITY" values (4, 'Vukovar', 'VU', '32000', 16);
insert into "CITY" values (5, 'Virovitica', 'VT', '33000', 10);
insert into "CITY" values (6, 'Sisak', 'SK', '44000', 3);
insert into "CITY" values (7, 'Split', 'ST', '21000', 17);

insert into "PERSON" values (1, 'Iva', 'Kapušin', 'iva.kapusin@gmail.com', 'ikapusin', 'password', '+385916568790', 'FEMALE', null, current_date);

insert into "PERSON_ROLE" values (1, 2);
insert into "PERSON_ROLE" values (1, 3);

insert into "MANAGER" values (1, '12121212121', true);

insert into "SALON" values (1, 'Frizerski studio MACHO', '33341234716', 'Siget ulica', '22', 'D', '+385916568790', 'ACTIVE', 1, current_date, 1, 21, 1);

insert into "ACCOMMODATION" values (1, 'Šišanje', 'Šišanje sa škarama i/ili mašinicom', 30, 20);
insert into "ACCOMMODATION" values (2, 'Uređivanje brade', 'Uređivanje brade sa škarama, mašinicom, žiletom', 20, 15);
insert into "ACCOMMODATION" values (3, 'Brijanje', 'Bijanje brade', 15, 10);
insert into "ACCOMMODATION" values (4, 'Farbanje', 'Farbanje kose sa bojom po želji', 45, 30);

insert into "SALON_ACCOMMODATION" values (1, 1);
insert into "SALON_ACCOMMODATION" values (1, 2);
insert into "SALON_ACCOMMODATION" values (1, 3);