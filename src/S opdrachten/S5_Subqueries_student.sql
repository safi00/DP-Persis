-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S5: Subqueries
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

-- Vervolgens kun je je uitwerkingen testen door de testregels
-- (met [TEST] erachter) te activeren (haal hiervoor de commentaartekens
-- weg) en vervolgens het hele bestand uit te voeren. Hiervoor moet je de
-- testsuite in de database hebben geladen (bedrijf_postgresql_test.sql).
-- NB: niet alle opdrachten hebben testregels.
--
-- Lever je werk pas in op Canvas als alle tests slagen.
-- ------------------------------------------------------------------------


-- S5.1. 
-- Welke medewerkers hebben zowel de Java als de XML cursus
-- gevolgd? Geef hun personeelsnummers.
DROP VIEW IF EXISTS s5_1; CREATE OR REPLACE VIEW    s5_1 AS                                                     -- [TEST]
Select mnr from medewerkers
left join inschrijvingen i on medewerkers.mnr = i.cursist
where cursus ='JAV'
intersect ( SELECT mnr from medewerkers
            intersect
            SELECT mnr from medewerkers
            left join inschrijvingen i on medewerkers.mnr = i.cursist
            where cursus ='XML')
;


-- S5.2.
-- Geef de nummers van alle medewerkers die niet aan de afdeling 'OPLEIDINGEN'
-- zijn verbonden.
DROP VIEW IF EXISTS s5_2; CREATE OR REPLACE VIEW s5_2 AS                                                     -- [TEST]
SELECT mnr from medewerkers
left join afdelingen a on medewerkers.afd = a.anr
EXCEPT (SELECT mnr from medewerkers
        left join afdelingen a on medewerkers.afd = a.anr
        where a.naam = (Select naam from afdelingen where naam ='OPLEIDINGEN') )
;

-- S5.3.
-- Geef de nummers van alle medewerkers die de Java-cursus niet hebben 
-- gevolgd.
DROP VIEW IF EXISTS s5_3; CREATE OR REPLACE VIEW s5_3 AS                                                     -- [TEST]
SELECT mnr from medewerkers
left join inschrijvingen i on medewerkers.mnr = i.cursist
except (    SELECT mnr from medewerkers
            intersect
            SELECT mnr from medewerkers
            left join inschrijvingen i on medewerkers.mnr = i.cursist
            where cursus ='JAV')
;

-- S5.4.
-- Welke medewerkers (naam) hebben ondergeschikten? En welke niet? (Je mag twee
-- queries gebruiken.)
DROP VIEW IF EXISTS s5_4; CREATE OR REPLACE VIEW s5_4 AS
-- SELECT mnr from medewerkers
-- intersect
-- SELECT chef from medewerkers
SELECT naam from medewerkers
    except
(SELECT a.naam from medewerkers a intersect select b.naam from medewerkers b left join medewerkers c on b.mnr = c.chef)
;

-- S5.5.
-- Geef cursuscode en begindatum van alle uitvoeringen van programmeercursussen
-- ('BLD') in 2020.
DROP VIEW IF EXISTS s5_5; CREATE OR REPLACE VIEW s5_5 AS                                                     -- [TEST]
select cursus, begindatum from uitvoeringen
left join cursussen c on c.code = uitvoeringen.cursus
where type = 'BLD' and begindatum > (select max(begindatum) from uitvoeringen where begindatum < '2020-01-01')
;

-- S5.6.
-- Geef van alle cursusuitvoeringen: de cursuscode, de begindatum en het 
-- aantal inschrijvingen (`aantal_inschrijvingen`). Sorteer op begindatum.
DROP VIEW IF EXISTS s5_6; CREATE OR REPLACE VIEW s5_6 AS                                                     -- [TEST]
select u.cursus, u.begindatum,count(cursist) as aantal_inschrijvingen
from uitvoeringen u
left join inschrijvingen on u.cursus = inschrijvingen.cursus and u.begindatum = inschrijvingen.begindatum
group by u.begindatum, u.cursus
order by u.begindatum
;

-- S5.7.
-- Geef voorletter(s) en achternaam van alle trainers die ooit tijdens een
-- algemene cursus hun eigen chef als cursist hebben gehad.
DROP VIEW IF EXISTS s5_7; CREATE OR REPLACE VIEW s5_7 AS                                                     -- [TEST]
SELECT (voorl, naam) as cursist from (select voorl, naam from medewerkers where functie = 'TRAINER') AS Cursist
;



-- S5.8.
-- Geef de naam van de medewerkers die nog nooit een cursus hebben gegeven.
DROP VIEW IF EXISTS s5_8; CREATE OR REPLACE VIEW s5_8 AS                                                     -- [TEST]
SELECT naam from medewerkers
    except
(SELECT a.naam from medewerkers a intersect select b.naam from medewerkers b left join uitvoeringen u on b.mnr = u.docent)
;


-- -------------------------[ HU TESTRAAMWERK ]--------------------------------
-- Met onderstaande query kun je je code testen. Zie bovenaan dit bestand
-- voor uitleg.

SELECT * FROM test_select('S5.1') AS resultaat
UNION
SELECT * FROM test_select('S5.2') AS resultaat
UNION
SELECT * FROM test_select('S5.3') AS resultaat
UNION
SELECT * FROM test_select('S5.4') AS resultaat
UNION
SELECT * FROM test_select('S5.5') AS resultaat
UNION
SELECT * FROM test_select('S5.6') AS resultaat
UNION
SELECT * FROM test_select('S5.7') AS resultaat
UNION
SELECT * FROM test_select('S5.8') AS resultaat
ORDER BY resultaat;
