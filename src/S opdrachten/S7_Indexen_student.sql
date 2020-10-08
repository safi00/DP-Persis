-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S7: Indexen
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------
-- LET OP, zoals in de opdracht op Canvas ook gezegd kun je informatie over
-- het query plan vinden op: https://www.postgresql.org/docs/current/using-explain.html


-- S7.1.
--
-- Je maakt alle opdrachten in de 'sales' database die je hebt aangemaakt en gevuld met
-- de aangeleverde data (zie de opdracht op Canvas).
--
-- Voer het voorbeeld uit wat in de les behandeld is:
-- 1. Voer het volgende EXPLAIN statement uit:
--    EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
--    Bekijk of je het resultaat begrijpt. Kopieer het explain plan onderaan de opdracht
-- 2. Voeg een index op stock_item_id toe:
--    CREATE INDEX ord_lines_si_id_idx ON order_lines (stock_item_id);
-- 3. Analyseer opnieuw met EXPLAIN hoe de query nu uitgevoerd wordt
--    Kopieer het explain plan onderaan de opdracht
-- 4. Verklaar de verschillen. Schrijf deze hieronder op.

EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
-- Gather  (cost=1000.00..6173.87 rows=1226 width=96)
--   Workers Planned: 2
--   ->  Parallel Seq Scan on order_lines  (cost=0.00..5051.27 rows=511 width=96)
--         Filter: (stock_item_id = 9)

CREATE INDEX ord_lines_si_id_idx ON order_lines (stock_item_id);

EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
-- Bitmap Heap Scan on order_lines  (cost=25.92..2608.51 rows=1226 width=96)
--   Recheck Cond: (stock_item_id = 9)
--   ->  Bitmap Index Scan on ord_lines_si_id_idx  (cost=0.00..25.62 rows=1226 width=0)
--         Index Cond: (stock_item_id = 9)




-- S7.2.
--
-- 1. Maak de volgende twee query’s:
-- 	  A. Toon uit de order tabel de order met order_id = 73590
-- 	  B. Toon uit de order tabel de order met customer_id = 1028
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

-- S7.3.A
--
-- Het blijkt dat customers regelmatig klagen over trage bezorging van hun bestelling.
-- Het idee is dat verkopers misschien te lang wachten met het invoeren van de bestelling in het systeem.
-- Daar willen we meer inzicht in krijgen.
-- We willen alle orders (order_id, order_date, salesperson_person_id (als verkoper),
--    het verschil tussen expected_delivery_date en order_date (als levertijd),  
--    en de bestelde hoeveelheid van een product zien (quantity uit order_lines).
-- Dit willen we alleen zien voor een bestelde hoeveelheid van een product > 250
--   (we zijn nl. als eerste geïnteresseerd in grote aantallen want daar lijkt het vaker mis te gaan)
-- En verder willen we ons focussen op verkopers wiens bestellingen er gemiddeld langer over doen.
-- De meeste bestellingen kunnen binnen een dag bezorgd worden, sommige binnen 2-3 dagen.
-- Het hele bestelproces is er op gericht dat de gemiddelde bestelling binnen 1.45 dagen kan worden bezorgd.
-- We willen in onze query dan ook alleen de verkopers zien wiens gemiddelde levertijd 
--  (expected_delivery_date - order_date) over al zijn/haar bestellingen groter is dan 1.45 dagen.
-- Maak om dit te bereiken een subquery in je WHERE clause.
-- Sorteer het resultaat van de hele geheel op levertijd (desc) en verkoper.
-- 1. Maak hieronder deze query (als je het goed doet zouden er 377 rijen uit moeten komen, en het kan best even duren...)

-- select (o.order_id, order_date, salesperson_person_id ) as verkoper, (expected_delivery_date - order_date) as levertijd_dagen, ol.quantity
-- from orders o
-- left join order_lines ol on o.order_id = ol.order_id
-- where quantity > 250 and (
--     select avg(expected_delivery_date - order_date) as gem
--     from orders
--     group by salesperson_person_id) > 1.45
-- group by verkoper,levertijd_dagen, ol.quantity
-- order by levertijd_dagen desc;

select o.order_id, o.order_date, o.salesperson_person_id as verkoper, (expected_delivery_date - order_date) as levertijd_dagen, ol.quantity
from orders o
left join order_lines ol on o.order_id = ol.order_id
left join (
    select x.salesperson_person_id, avg(x.expected_delivery_date - x.order_date) as average
    from orders x
    group by x.salesperson_person_id) a on o.salesperson_person_id = a.salesperson_person_id
where quantity > 250 and a.average > 1.45
group by o.order_id, order_date, verkoper,levertijd_dagen, ol.quantity
order by levertijd_dagen desc;

-- S7.3.B
--
-- 1. Vraag het EXPLAIN plan op van je query (kopieer hier, onder de opdracht)
-- 2. Kijk of je met 1 of meer indexen de query zou kunnen versnellen
-- 3. Maak de index(en) aan en run nogmaals het EXPLAIN plan (kopieer weer onder de opdracht) 
-- 4. Wat voor verschillen zie je? Verklaar hieronder.

EXPLAIN select o.order_id, o.order_date, o.salesperson_person_id as verkoper, (expected_delivery_date - order_date) as levertijd_dagen, ol.quantity
from orders o
left join order_lines ol on o.order_id = ol.order_id
left join (
    select x.salesperson_person_id, avg(x.expected_delivery_date - x.order_date) as average
    from orders x
    group by x.salesperson_person_id) a on o.salesperson_person_id = a.salesperson_person_id
where quantity > 250 and a.average > 1.45
group by o.order_id, order_date, verkoper,levertijd_dagen, ol.quantity
order by levertijd_dagen desc;

-- Group  (cost=9749.92..9781.64 rows=287 width=24)
-- "  Group Key: ((o.expected_delivery_date - o.order_date)), o.order_id, ol.quantity"
--   ->  Gather Merge  (cost=9749.92..9779.12 rows=240 width=20)
--         Workers Planned: 2
--         ->  Group  (cost=8749.89..8751.39 rows=120 width=20)
-- "              Group Key: ((o.expected_delivery_date - o.order_date)), o.order_id, ol.quantity"
--               ->  Sort  (cost=8749.89..8750.19 rows=120 width=20)
-- "                    Sort Key: ((o.expected_delivery_date - o.order_date)) DESC, o.order_id, ol.quantity"
--                     ->  Hash Join  (cost=2188.42..8745.75 rows=120 width=20)
--                           Hash Cond: (o.salesperson_person_id = a.salesperson_person_id)
--                           ->  Nested Loop  (cost=0.29..6555.83 rows=399 width=20)
--                                 ->  Parallel Seq Scan on order_lines ol  (cost=0.00..5051.27 rows=399 width=8)
--                                       Filter: (quantity > 250)
--                                 ->  Index Scan using pk_sales_orders on orders o  (cost=0.29..3.77 rows=1 width=16)
--                                       Index Cond: (order_id = ol.order_id)
--                           ->  Hash  (cost=2188.09..2188.09 rows=3 width=4)
--                                 ->  Subquery Scan on a  (cost=2187.91..2188.09 rows=3 width=4)
--                                       ->  HashAggregate  (cost=2187.91..2188.06 rows=3 width=36)
--                                             Group Key: x.salesperson_person_id
--                                             Filter: (avg((x.expected_delivery_date - x.order_date)) > 1.45)
--                                             ->  Seq Scan on orders x  (cost=0.00..1635.95 rows=73595 width=12)

CREATE INDEX order_lines_qua_idx ON order_lines (quantity);
CREATE INDEX orders_salesperson_person_id_idx ON orders (salesperson_person_id);

-- Group  (cost=6528.77..6532.36 rows=287 width=24)
-- "  Group Key: ((o.expected_delivery_date - o.order_date)), o.order_id, ol.quantity"
--   ->  Sort  (cost=6528.77..6529.49 rows=287 width=20)
-- "        Sort Key: ((o.expected_delivery_date - o.order_date)) DESC, o.order_id, ol.quantity"
--         ->  Hash Join  (cost=4436.86..6517.05 rows=287 width=20)
--               Hash Cond: (o.order_id = ol.order_id)
--               ->  Hash Join  (cost=2188.13..4099.15 rows=22078 width=16)
--                     Hash Cond: (o.salesperson_person_id = a.salesperson_person_id)
--                     ->  Seq Scan on orders o  (cost=0.00..1635.95 rows=73595 width=16)
--                     ->  Hash  (cost=2188.09..2188.09 rows=3 width=4)
--                           ->  Subquery Scan on a  (cost=2187.91..2188.09 rows=3 width=4)
--                                 ->  HashAggregate  (cost=2187.91..2188.06 rows=3 width=36)
--                                       Group Key: x.salesperson_person_id
--                                       Filter: (avg((x.expected_delivery_date - x.order_date)) > 1.45)
--                                       ->  Seq Scan on orders x  (cost=0.00..1635.95 rows=73595 width=12)
--               ->  Hash  (cost=2236.77..2236.77 rows=957 width=8)
--                     ->  Bitmap Heap Scan on order_lines ol  (cost=19.84..2236.77 rows=957 width=8)
--                           Recheck Cond: (quantity > 250)
--                           ->  Bitmap Index Scan on order_lines_qua_idx  (cost=0.00..19.60 rows=957 width=0)
--                                 Index Cond: (quantity > 250)

-- cost=9749.92..9781.64 to cost=6528.77..6532.36
-- alot faster and the 2nd explain had alot less grouping

-- S7.3.C
--
-- Zou je de query ook heel anders kunnen schrijven om hem te versnellen?

-- i cant think of a way to make it the query any faster
