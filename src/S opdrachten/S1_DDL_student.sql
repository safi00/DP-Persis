-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S1: Data Definition Language
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
-- Lever je werk pas in op Canvas als alle tests slagen. Draai daarna
-- alle wijzigingen in de database terug met de queries helemaal onderaan.
-- ------------------------------------------------------------------------


-- S1.1. Geslacht
--
-- Voeg een kolom `geslacht` toe aan de medewerkerstabel.
-- Voeg ook een beperkingsregel `m_geslacht_chk` toe aan deze kolom,
-- die ervoor zorgt dat alleen 'M' of 'V' als geldige waarde wordt
-- geaccepteerd. Test deze regel en neem de gegooide foutmelding op als
-- commentaar in de uitwerking.
ALTER TABLE Medewerkers ADD COLUMN Geslacht char(10);
ALTER TABLE Medewerkers ADD CONSTRAINT m_geslacht_chk CHECK (geslacht = 'V' OR geslacht = 'M');

INSERT INTO medewerkers (mnr, naam, voorl, functie, chef, gbdatum, maandsal, comm, afd, geslacht)
 VALUES (8000, 'DONK', 'A', 'DIRECTEUR', 7839, '17-12-1901', 5000, NULL, null,'b');

-- [2020-09-21 20:45:23] [23514] ERROR: new row for relation "medewerkers" violates check constraint "m_geslacht_chk"
-- [2020-09-21 20:45:23] Detail: Failing row contains (8000, DONK, A, DIRECTEUR, 7839, 1901-12-17, 5000.00, null, null, b         ).

delete from medewerkers where mnr = 8000;
-- S1.2. Nieuwe afdeling
--
-- Het bedrijf krijgt een nieuwe onderzoeksafdeling 'ONDERZOEK' in Zwolle.
-- Om de onderzoeksafdeling op te zetten en daarna te leiden wordt de
-- nieuwe medewerker A DONK aangenomen. Hij krijgt medewerkersnummer 8000
-- en valt direct onder de directeur.
-- Voeg de nieuwe afdeling en de nieuwe medewerker toe aan de database.
SET DATESTYLE = 'DMY';

INSERT INTO medewerkers (mnr, naam, voorl, functie, chef, gbdatum, maandsal, comm, afd, geslacht)
 VALUES (8000, 'DONK', 'A', 'DIRECTEUR', 7839, '17-12-1901', 5000, NULL, null,'M');

INSERT INTO afdelingen (anr, naam, locatie,hoofd) VALUES (50, 'ONDERZOEK', 'ZWOLLE',8000);

UPDATE medewerkers set afd = 50 where mnr = 8000;

-- S1.3. Verbetering op afdelingentabel
--
-- We gaan een aantal verbeteringen doorvoeren aan de tabel `afdelingen`:
--   a) Maak een sequence die afdelingsnummers genereert. Denk aan de beperking
--      dat afdelingsnummers veelvouden van 10 zijn.
--   b) Voeg een aantal afdelingen toe aan de tabel, maak daarbij gebruik van
--      de nieuwe sequence.
--   c) Op enig moment gaat het mis. De betreffende kolommen zijn te klein voor
--      nummers van 3 cijfers. Los dit probleem op.
SELECT * from afdelingen;
CREATE SEQUENCE afdelingen_afdnummer INCREMENT BY 10 MINVALUE 50 MAXVALUE 200 owned by afdelingen.anr;
INSERT INTO afdelingen(anr, naam, locatie, hoofd)
VALUES
       (nextval('afdelingen_afdnummer'),'COOKIE','LEIDEN',8000),
       (nextval('afdelingen_afdnummer'),'WAFFLE','LEIDEN',8000),
       (nextval('afdelingen_afdnummer'),'PANCAKE','LEIDEN',8000);

ALTER TABLE afdelingen ALTER COLUMN anr  TYPE numeric(3);

-- S1.4. Adressen
--
-- Maak een tabel `adressen`, waarin de adressen van de medewerkers worden
-- opgeslagen (inclusief adreshistorie). De tabel bestaat uit onderstaande
-- kolommen. Voeg minimaal één rij met adresgegevens van A DONK toe.
--
--    postcode      PK, bestaande uit 6 karakters (4 cijfers en 2 letters)
--    huisnummer    PK
--    ingangsdatum  PK
--    einddatum     moet na de ingangsdatum liggen
--    telefoon      10 cijfers, uniek
--    med_mnr       FK, verplicht
CREATE TABLE adressen
(
    postcode     VARCHAR(6) CONSTRAINT m_pst_chk CHECK (postcode LIKE '[0-9][0-9][0-9][0-9][A-Z][A-Z]'),
    huisnummer   VARCHAR(2),
    ingangsdatum DATE,
    einddatum    DATE CHECK ( einddatum > ingangsdatum ) ,
    telefoon     NUMERIC(10) UNIQUE,
    med_mnr NUMERIC(10) not null,
    PRIMARY KEY (postcode, huisnummer,ingangsdatum),
    FOREIGN KEY(med_mnr)REFERENCES medewerkers(mnr)
);
drop table adressen;

-- S1.5. Commissie
--
-- De commissie van een medewerker (kolom `comm`) moet een bedrag bevatten als de medewerker een functie als
-- 'VERKOPER' heeft, anders moet de commissie NULL zijn. Schrijf hiervoor een beperkingsregel. Gebruik onderstaande
-- 'illegale' INSERTs om je beperkingsregel te controleren.
ALTER TABLE medewerkers ADD CONSTRAINT m_comm_chk CHECK (functie = 'VERKOPER' AND comm IS NOT NULL OR functie != 'VERKOPER' AND comm IS NULL);

DELETE FROM medewerkers
WHERE mnr = 8001;

INSERT INTO medewerkers (mnr, naam, voorl, functie, chef, gbdatum, maandsal, comm)
VALUES (8001, 'MULLER', 'TJ', 'TRAINER', 7566, '1982-08-18', 2000, 500);

INSERT INTO medewerkers (mnr, naam, voorl, functie, chef, gbdatum, maandsal, comm)
VALUES (8002, 'JANSEN', 'M', 'VERKOPER', 7698, '1981-07-17', 1000, NULL);

--[2020-09-14 15:24:03] [23514] ERROR: new row for relation "medewerkers" violates check constraint "m_comm_chk"
--[2020-09-14 15:24:03] Detail: Failing row contains (8001, MULLER, TJ, TRAINER, 7566, 1982-08-18, 2000.00, 500.00, 10, null).

--[2020-09-14 15:24:45] [23514] ERROR: new row for relation "medewerkers" violates check constraint "m_comm_chk"
--[2020-09-14 15:24:45] Detail: Failing row contains (8002, JANSEN, M, VERKOPER, 7698, 1981-07-17, 1000.00, null, 10, null).

--Deze zijn de foutmeldingen dat ik krijg met de CONSTRAINT dat ik heb gezet




-- -------------------------[ HU TESTRAAMWERK ]--------------------------------
-- Met onderstaande query kun je je code testen. Zie bovenaan dit bestand
-- voor uitleg.

SELECT * FROM test_exists('S1.1', 1) AS resultaat
UNION
SELECT * FROM test_exists('S1.2', 1) AS resultaat
UNION
SELECT 'S1.3 wordt niet getest: geen test mogelijk.' AS resultaat
UNION
SELECT * FROM test_exists('S1.4', 6) AS resultaat
UNION
SELECT 'S1.5 wordt niet getest: handmatige test beschikbaar.' AS resultaat
ORDER BY resultaat;


-- Draai alle wijzigingen terug om conflicten in komende opdrachten te voorkomen.
UPDATE medewerkers SET afd = NULL WHERE mnr < 7369 OR mnr > 7934;
UPDATE afdelingen SET hoofd = NULL WHERE anr > 40;
DELETE FROM afdelingen WHERE anr > 40;
DELETE FROM medewerkers WHERE mnr < 7369 OR mnr > 7934;
ALTER TABLE medewerkers DROP CONSTRAINT IF EXISTS m_geslacht_chk;
ALTER TABLE medewerkers DROP COLUMN IF EXISTS geslacht;
