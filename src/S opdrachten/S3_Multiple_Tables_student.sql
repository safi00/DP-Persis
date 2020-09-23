-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S3: Multiple Tables
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
--
--
-- Opdracht: schrijf SQL-queries om onderstaande resultaten op te vragen,
-- aan te maken, verwijderen of aan te passen in de database van de
-- bedrijfscasus.
--
-- Codeer je uitwerking onder de regel 'DROP VIEW ...' (bij een SELECT)
-- of boven de regel 'ON CONFLICT DO NOTHING;' (bij een INSERT)
-- Je kunt deze eigen query selecteren en los uitvoeren, en wijzigen tot
-- je tevreden bent.
--
-- Vervolgens kun je je uitwerkingen testen door de testregels
-- (met [TEST] erachter) te activeren (haal hiervoor de commentaartekens
-- weg) en vervolgens het hele bestand uit te voeren. Hiervoor moet je de
-- testsuite in de database hebben geladen (bedrijf_postgresql_test.sql).
-- NB: niet alle opdrachten hebben testregels.
--
-- Lever je werk pas in op Canvas als alle tests slagen.
-- ------------------------------------------------------------------------


-- S3.1.
-- Produceer een overzicht van alle cursusuitvoeringen; geef de
-- cursuscode, de begindatum, de cursuslengte en de naam van de docent.
DROP VIEW IF EXISTS s3_1; CREATE OR REPLACE VIEW s3_1 AS                                                -- [TEST]
select co.cursus as code, co.begindatum as begindatum, cu.lengte as lengte, mede.naam as Docentnaam FROM uitvoeringen co
inner join cursussen cu ON co.cursus = cu.code
inner join medewerkers mede on co.docent = mede.mnr;


-- S3.2.
-- Geef in twee kolommen naast elkaar de naam van elke cursist (`cursist`)
-- die een S02-cursus heeft gevolgd, met de naam van de docent (`docent`).
DROP VIEW IF EXISTS s3_2; CREATE OR REPLACE VIEW s3_2 AS                                                     -- [TEST]
SELECT cursist.naam AS cursistnamen, mede.naam AS docentnamen FROM medewerkers cursist
INNER JOIN inschrijvingen a ON cursist.mnr = a.cursist
INNER JOIN uitvoeringen b ON a.begindatum = b.begindatum AND a.cursus = b.cursus
INNER JOIN medewerkers mede ON mede.mnr = b.docent
WHERE a.cursus = 'S02';

-- S3.3.
-- Geef elke afdeling (`afdeling`) met de naam van het hoofd van die
-- afdeling (`hoofd`).
DROP VIEW IF EXISTS s3_3; CREATE OR REPLACE VIEW s3_3 AS                                                     -- [TEST]
-- INSERT INTO afdelingen(anr, naam, locatie, hoofd) VALUES (60, 'FINANCIEN', 'LEERDAM', NULL);
-- this was to test the left join
select a.naam as afdeling, mede.naam as hoofd from afdelingen a
LEFT join medewerkers mede ON a.hoofd = mede.mnr;


-- S3.4.
-- Geef de namen van alle medewerkers, de naam van hun afdeling (`afdeling`)
-- en de bijbehorende locatie.
DROP VIEW IF EXISTS s3_4; CREATE OR REPLACE VIEW s3_4 AS
-- INSERT INTO medewerkers(mnr, naam, voorl, functie, chef, gbdatum, maandsal, comm, afd, geslacht) VALUES (8069, 'MARTINA', 'X', 'DIRECTUER', NULL,'1997-10-27', 5000.00, NULL, NULL, 'M');
-- this was to test the left join
select mede.naam as medewerkers, afd.naam as afdnaam, afd.locatie as locatie from medewerkers mede
left join afdelingen afd ON afd.anr = mede.afd;


-- S3.5.
-- Geef de namen van alle cursisten die staan ingeschreven voor de cursus S02 van 12 april 2019
DROP VIEW IF EXISTS s3_5; CREATE OR REPLACE VIEW s3_5 AS                                                     -- [TEST]
select cursist.naam AS cursistnamen FROM inschrijvingen insch
LEFT JOIN medewerkers cursist ON cursist.mnr = insch.cursist
WHERE cursus = 'S02' AND begindatum = '2019-04-12';

-- S3.6.
-- Geef de namen van alle medewerkers en hun toelage.
DROP VIEW IF EXISTS s3_6; CREATE OR REPLACE VIEW s3_6 AS                                                     -- [TEST]
SELECT mede.naam AS medewerkernamen,scha.toelage FROM medewerkers mede
inner join schalen scha ON scha.bovengrens > mede.maandsal AND scha.ondergrens < mede.maandsal;


-- -------------------------[ HU TESTRAAMWERK ]--------------------------------
-- Met onderstaande query kun je je code testen. Zie bovenaan dit bestand
-- voor uitleg.

SELECT * FROM test_select('S3.1') AS resultaat
UNION
SELECT * FROM test_select('S3.2') AS resultaat
UNION
SELECT * FROM test_select('S3.3') AS resultaat
UNION
SELECT * FROM test_select('S3.4') AS resultaat
UNION
SELECT * FROM test_select('S3.5') AS resultaat
UNION
SELECT * FROM test_select('S3.6') AS resultaat
ORDER BY resultaat;

