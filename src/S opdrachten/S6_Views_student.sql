-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S6: Views
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------


-- S6.1.
--
-- 1. Maak een view met de naam "deelnemers" waarmee je de volgende gegevens uit de tabellen inschrijvingen en uitvoering combineert:
--    inschrijvingen.cursist, inschrijvingen.cursus, inschrijvingen.begindatum, uitvoeringen.docent, uitvoeringen.locatie
-- 2. Gebruik de view in een query waarbij je de "deelnemers" view combineert met de "personeels" view (behandeld in de les):
--     CREATE OR REPLACE VIEW personeel AS
-- 	     SELECT mnr, voorl, naam as medewerker, afd, functie
--       FROM medewerkers;
-- 3. Is de view "deelnemers" updatable ? Waarom ?

CREATE OR REPLACE VIEW deelnemers AS
SELECT inschrijvingen.cursist, inschrijvingen.cursus, inschrijvingen.begindatum, u.docent, u.locatie
FROM inschrijvingen
inner join uitvoeringen u on u.cursus = inschrijvingen.cursus and u.begindatum = inschrijvingen.begindatum;

SELECT * from deelnemers;

CREATE OR REPLACE VIEW personeel AS
SELECT mnr, voorl, naam as medewerker, afd, functie
FROM medewerkers;

SELECT * from personeel;

select p.*, d.* from deelnemers d
inner join personeel p on p.mnr = d.cursist;

-- Because it's a relatively simple view so it would have no issues being updated
-- it doesn't have SUM(), MIN(), MAX(), COUNT(), DISTINCT GROUP BY HAVING UNION or UNION ALL Subquery in the select



-- S6.2.
--
-- 1. Maak een view met de naam "dagcursussen". Deze view dient de gegevens op te halen: 
--      code, omschrijving en type uit de tabel curssussen met als voorwaarde dat de lengte = 1. Toon aan dat de view werkt. 
-- 2. Maak een tweede view met de naam "daguitvoeringen". 
--    Deze view dient de uitvoeringsgegevens op te halen voor de "dagcurssussen" (gebruik ook de view "dagcursussen"). Toon aan dat de view werkt
-- 3. Verwijder de views en laat zien wat de verschillen zijn bij DROP view <viewnaam> CASCADE en bij DROP view <viewnaam> RESTRICT

CREATE OR REPLACE VIEW dagcursussen AS
SELECT code, omschrijving, type FROM cursussen
where lengte = 1;
-- select * from dagcursussen;
-- SELECT * from uitvoeringen;

CREATE OR REPLACE VIEW daguitvoeringen AS
SELECT d.*, u.* from dagcursussen d
inner join uitvoeringen u on d.code = u.cursus;
-- select * from daguitvoeringen;

drop view dagcursussen CASCADE;
-- [2020-10-06 15:41:40] [00000] drop cascades to view daguitvoeringen
-- [2020-10-06 15:41:40] completed in 221 ms

drop view dagcursussen RESTRICT ;
-- [2020-10-06 15:42:21] completed in 39 ms

drop view daguitvoeringen cascade ;
-- [2020-10-06 15:43:23] [42P01] ERROR: view "daguitvoeringen" does not exist

drop view daguitvoeringen RESTRICT ;
-- [2020-10-06 15:46:02] completed in 10 ms