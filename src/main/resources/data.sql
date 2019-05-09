insert into users(username,password,enabled) values ('admin','$2a$10$Bwf0zubQRO6/niKOP4vA6.NnltBJPW/9JyztwQ3NU5jXv1Oby4z2K',true);
insert into authorities(username,authority) values('admin','ROLE_ADMIN');

insert into books(isbn,author,title,username) values ('1','K.Safone','Shadow of wind',null);
insert into books(isbn,author,title,username) values ('2','A.Pushkin','The golden fish','admin');
insert into books(isbn,author,title,username) values ('3','N.Gogol','Dead souls',null);
insert into books(isbn,author,title,username) values ('4','R.Murakami','Extas',null);
insert into books(isbn,author,title,username) values ('5','N.Geymann','American Gods',null);
insert into books(isbn,author,title,username) values ('6','S.Chegeneva','Coffee wolf',null);
insert into books(isbn,author,title,username) values ('7','I.Milovanov','Spring is awesome',null);
insert into books(isbn,author,title,username) values ('8','M.Milovanov','Valerion',null);
insert into books(isbn,author,title,username) values ('9','O.Lubysheva','Drinking Partner',null);
insert into books(isbn,author,title,username) values ('10','J.Pikulina','It`s so funny and stupid',null);
insert into books(isbn,author,title,username) values ('11','A.Pisareff','Do it yourself',null);