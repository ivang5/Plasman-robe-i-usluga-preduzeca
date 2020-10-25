set foreign_key_checks = 0;
-- delete all rows
truncate table preduzece;
truncate table zaposleni;
truncate table authority;
truncate table user;
truncate table user_authority;
truncate table cenovnik;
truncate table proizvodi;
truncate table stavke_cenovnika;
truncate table racuni_u_banci;
truncate table grupe_proizvoda;
truncate table predracuni;
truncate table pdv_kategorije;
truncate table pdv_stope;
truncate table poslovne_godine;
truncate table poslovni_partneri;

truncate table stavke_fakture;
truncate table stavke_predracuna;
truncate table fakture;

set foreign_key_checks = 1;

insert into preduzece (pib, naziv, telefon) values (110652405, 'ZZ KETIN', '0118871002');

insert into zaposleni (ime, prezime, adresa, broj_telefona, uloga, id_preduzeca, obrisan) values ('Dragan', 'Ketin', 'Tome Zdravkovica 6 Botos', '0692345678', 0, 1, false);
insert into zaposleni (ime, prezime, adresa, broj_telefona, uloga, id_preduzeca, obrisan) values ('Biljana', 'Ketin', 'Tome Zdravkovica 6 Botos', '0645675678', 1, 1, false);
insert into zaposleni (ime, prezime, adresa, broj_telefona, uloga, id_preduzeca, obrisan) values ('Petar', 'Petrovic', 'Brace Ahmedovski 12 Tomasevac', '0612345678', 3, 1, false);

insert into authority (name) values ('ROLE_ADMIN');
insert into authority (name) values ('ROLE_USER');

-- password is 'admin' (bcrypt encoded) 
insert into user (username, password, id_zaposlenog) values ('admin', '$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK', 1);
-- password is 'user' (bcrypt encoded)
insert into user (username, password, id_zaposlenog) values ('user', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq', 3);

insert into user_authority (user_id, authority_id) values (1, 1); -- admin has ROLE_ADMIN
insert into user_authority (user_id, authority_id) values (2, 2); -- user has ROLE_USER

insert into poslovni_partneri (pib, naziv, vrsta_partnera, adresa, telefon, email, obrisan) values (110656545, 'Zrnex D.O.O.', 2, 'Petra Drapsina 101 Novi Becej', '026566566', 'info@zrnex.com', false);
insert into poslovni_partneri (pib, naziv, vrsta_partnera, adresa, telefon, email, obrisan) values (100215149, 'Agrofinans D.O.O.', 0, 'Sime Solaje 43 Beograd', '011533533', 'info@agrofinans.com', false);

insert into pdv_kategorije (naziv) values ('Posebna stopa PDV')
insert into pdv_kategorije (naziv) values ('Opsta stopa PDV')

insert into pdv_stope (procenat, datum_pocetka_vazenja, aktivna, id_pdv_kategorije, obrisan) values (8, '2020-01-01', true, 1, false)
insert into pdv_stope (procenat, datum_pocetka_vazenja, aktivna, id_pdv_kategorije, obrisan) values (10, '2019-01-01', false, 1, false)
insert into pdv_stope (procenat, datum_pocetka_vazenja, aktivna, id_pdv_kategorije, obrisan) values (20, '2020-01-01', true, 2, false)

insert into grupe_proizvoda (naziv, id_preduzeca, id_pdv_kategorije, obrisan) values ('zitarice', 1, 1, false)
insert into grupe_proizvoda (naziv, id_preduzeca, id_pdv_kategorije, obrisan) values ('voce i povrce', 1, 1, false)
insert into grupe_proizvoda (naziv, id_preduzeca, id_pdv_kategorije, obrisan) values ('poljoprivredne usluge', 1, 2, false)
insert into grupe_proizvoda (naziv, id_preduzeca, id_pdv_kategorije, obrisan) values ('hrana za stoku', 1, 1, false)
insert into grupe_proizvoda (naziv, id_preduzeca, id_pdv_kategorije, obrisan) values ('ziva stoka', 1, 1, false)

insert into cenovnik (datum_izdavanja) values ('2020-07-07');

insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('kukuruz', 'kg', 'kukuruz u zrnu', 0, false, 1);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('psenica', 'kg', 'seme psenice', 0, false, 1);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('jecam', 'kg', 'seme jecma', 0, false, 1);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('usluga kombajniranja', 'ha', 'usluga kombajniranja', 1, false, 3);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('usluga oranja', 'ha', 'usluga oranja', 1, false, 3);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('suncokret', 'kg', 'seme suncokreta', 0, false, 1);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('usluga baliranja', 'ha', 'usluga baliranja', 1, false, 3);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('ovas', 'kg', 'seme ovsa', 0, false, 1);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('paprika', 'kg', 'paprika', 0, false, 2);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('maline', 'kg', 'maline', 0, false, 2);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('merkantilni kukuruz', 'kg', 'otpad kukuruza', 0, false, 1);
insert into proizvodi (naziv, jedinica_mere, opis, tip_proizvoda, obrisan, id_grupe) values ('merkantilna psenica', 'kg', 'otpad psenice', 0, false, 1);

insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (1, 17, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (2, 15, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (3, 16, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (4, 5000, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (5, 2500, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (6, 23, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (7, 4000, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (8, 15, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (9, 100, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (10, 140, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (11, 8, 1);
insert into stavke_cenovnika (id_proizvoda, cena, id_cenovnika) values (12, 6, 1);

insert into racuni_u_banci (naziv_banke, broj_racuna, obrisan, poslovni_partner_id) values ('Banka Intesa', '170-1344103732391-81', false, 1)
insert into racuni_u_banci (naziv_banke, broj_racuna, obrisan, poslovni_partner_id) values ('Raiffeisen banka', '850-1344145632391-32', false, 1)
insert into racuni_u_banci (naziv_banke, broj_racuna, obrisan, preduzece_id) values ('Credit Agricole S.A.', '330-1344103732391-27', false, 1)
insert into racuni_u_banci (naziv_banke, broj_racuna, obrisan, preduzece_id) values ('Raiffeisen banka', '850-1324000002337-32', false, 1)

insert into poslovne_godine (godina, zakljucena, id_preduzeca) values (2020, false, 1)

insert into fakture (id_zaposlenog, datum_fakture, datum_valute, broj_racuna, iznos, pdv, ukupan_rabat, status, id_poslovnog_partnera, poziv_na_broj, id_poslovne_godine) values (2, '2020-06-08 06:14:00', '2020-06-08 06:14:00', '330-1344103732391-27', 259716.49, 25971.65, 0, 1, 1, '1-1-500-2020', 2020)
insert into fakture (id_zaposlenog, datum_fakture, datum_valute, broj_racuna, iznos, pdv, ukupan_rabat, status, id_poslovnog_partnera, poziv_na_broj, id_poslovne_godine) values (2, '2020-07-08 06:14:00', '2020-07-08 06:14:00', '330-1344103732391-27', 325600, 32560, 0, 1, 1, '1-1-501-2020', 2020)

insert into stavke_fakture (id_proizvoda, kolicina, cena, pdv, rabat, iznos, id_fakture) values (6, 1000, 56.09, 10, 0, 61708.13, 1)
insert into stavke_fakture (id_proizvoda, kolicina, cena, pdv, rabat, iznos, id_fakture) values (3, 4000, 45, 10, 0, 198008.36, 1)
insert into stavke_fakture (id_proizvoda, kolicina, cena, pdv, rabat, iznos, id_fakture) values (1, 10000, 17.00, 10, 0, 187000, 2)
insert into stavke_fakture (id_proizvoda, kolicina, cena, pdv, rabat, iznos, id_fakture) values (2, 7000, 18, 10, 0, 138600, 2)

insert into predracuni (id_kupca, broj_racuna, iznos, pdv, ukupan_rabat, rok_isporuke, rok_placanja, id_zaposlenog, poslovna_godina) values (2, '330-1344103732391-27', 500000, 50000, 0, '23.10.2020', '26.10.2020', 3, 2020)

insert into stavke_predracuna (id_proizvoda, kolicina, cena, pdv, rabat, iznos, id_predracuna) values (6, 15151.515, 30, 10, 0, 500000, 1)


