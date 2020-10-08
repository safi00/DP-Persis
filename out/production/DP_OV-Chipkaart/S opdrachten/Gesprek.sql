-- S1.4. Adressen
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

CREATE TABLE adressen(
    postcode     VARCHAR(6) CONSTRAINT m_pst_chk CHECK (postcode LIKE '[0-9][0-9][0-9][0-9][A-Z][A-Z]'),
    huisnummer   VARCHAR(2),
    ingangsdatum DATE,
    einddatum    DATE CHECK ( einddatum > ingangsdatum ) ,
    telefoon     NUMERIC(10) UNIQUE,
    med_mnr NUMERIC(10) not null,
    PRIMARY KEY (postcode, huisnummer,ingangsdatum),
    FOREIGN KEY(med_mnr)REFERENCES medewerkers(mnr)
);

drop TABLE adressen;

select * from adressen;

-- S2.2. Medewerkersoverzicht
-- Geef alle informatie van alle medewerkers, gesorteerd op functie,
-- en per functie op leeftijd (van jong naar oud).

DROP VIEW IF EXISTS s2_2; CREATE OR REPLACE VIEW s2_2 AS                                                     -- [TEST]
select naam, voorl from medewerkers ORDER BY functie ASC, gbdatum DESC;

-- S2.7. Nieuwe schaal
-- We breiden het salarissysteem uit naar zes schalen. Voer een extra schaal in voor mensen die
-- tussen de 3001 en 4000 euro verdienen. Zij krijgen een toelage van 500 euro.

UPDATE schalen SET ondergrens = 4001, bovengrens = 9999 WHERE snr = 6;
INSERT INTO schalen(snr, ondergrens, bovengrens, toelage) VALUES (6,4001.00,9999.00,500.00)
ON CONFLICT DO NOTHING;                                                                                         -- [TEST]

-- S3.2.
-- Geef in twee kolommen naast elkaar de naam van elke cursist (`cursist`)
-- die een S02-cursus heeft gevolgd, met de naam van de docent (`docent`).

DROP VIEW IF EXISTS s3_2; CREATE OR REPLACE VIEW s3_2 AS                                                     -- [TEST]
SELECT cursist.naam AS cursist, mede.naam AS docent FROM medewerkers cursist
Left JOIN inschrijvingen a ON cursist.mnr = a.cursist
left JOIN uitvoeringen b ON a.begindatum = b.begindatum AND a.cursus = b.cursus
left JOIN medewerkers mede ON mede.mnr = b.docent
WHERE a.cursus = 'S02';

-- S4.3.
-- Geef nu code, begindatum en aantal inschrijvingen (`aantal_inschrijvingen`) van alle
-- cursusuitvoeringen in 2019 met minstens drie inschrijvingen.

DROP VIEW IF EXISTS s4_3; CREATE OR REPLACE VIEW s4_3 AS                                                     -- [TEST]
select i.cursus as code, i.begindatum, count(i.cursus) as aantal_inschrijvingen from inschrijvingen i
left join uitvoeringen u on i.cursus = u.cursus and i.begindatum = u.begindatum
where u.begindatum > '2018-12-31' AND u.begindatum < '2020-01-01'
group by i.cursus, i.begindatum
having count(i.cursus) >= 3;

-- S5.7.
-- Geef voorletter(s) en achternaam van alle trainers die ooit tijdens een
-- algemene cursus hun eigen chef als cursist hebben gehad.

DROP VIEW IF EXISTS s5_7; CREATE OR REPLACE VIEW s5_7 AS                                                     -- [TEST]
select voorl, naam from medewerkers
where functie = 'TRAINER' and chef in (
    select cursist from inschrijvingen
    join uitvoeringen u on u.cursus = inschrijvingen.cursus and u.begindatum = inschrijvingen.begindatum
    where u.docent=medewerkers.mnr);

-- S6.1.
-- 1. Maak een view met de naam "deelnemers" waarmee je de volgende gegevens uit de tabellen inschrijvingen en uitvoering combineert:
--    inschrijvingen.cursist, inschrijvingen.cursus, inschrijvingen.begindatum, uitvoeringen.docent, uitvoeringen.locatie
-- 2. Gebruik de view in een query waarbij je de "deelnemers" view combineert met de "personeels" view (behandeld in de les):
--     CREATE OR REPLACE VIEW personeel AS
--          SELECT mnr, voorl, naam as medewerker, afd, functie
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


-- S7.2.
-- 1. Maak de volgende twee query’s:
--       A. Toon uit de order tabel de order met order_id = 73590
--       B. Toon uit de order tabel de order met customer_id = 1028
-- 2. Analyseer met EXPLAIN hoe de query’s uitgevoerd worden en kopieer het explain plan onderaan de opdracht
-- 3. Verklaar de verschillen en schrijf deze op
-- 4. Voeg een index toe, waarmee query B versneld kan worden
-- 5. Analyseer met EXPLAIN en kopieer het explain plan onder de opdracht
-- 6. Verklaar de verschillen en schrijf hieronder op
select * from orders where order_id = 73590;
select * from orders where customer_id = 1028;

EXPLAIN select * from orders where order_id = 73590;
-- Index Scan using pk_sales_orders on orders  (cost=0.29..8.31 rows=1 width=155)
--   Index Cond: (order_id = 73590)
EXPLAIN select * from orders where customer_id = 1028;
-- Seq Scan on orders  (cost=0.00..1819.94 rows=107 width=155)
--   Filter: (customer_id = 1028)

-- one is already using an index and there for is running faster
-- and it's only returning one row

CREATE INDEX orders_cust_id_idx ON orders (customer_id);

EXPLAIN select * from orders where customer_id = 1028;
-- Bitmap Heap Scan on orders  (cost=5.12..308.96 rows=107 width=155)
--   Recheck Cond: (customer_id = 1028)
--   ->  Bitmap Index Scan on orders_cust_id_idx  (cost=0.00..5.10 rows=107 width=0)
--         Index Cond: (customer_id = 1028)

-- cost=5.12..308.96 to cost=0.00..5.10 way faster