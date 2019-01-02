--Liam Fruzyna
--COSC 4800 In Class 10-30-17

--a
SELECT pname
FROM parts
WHERE price < 20;

--b
SELECT e.ename, z.city
FROM employees e, zipcodes z
WHERE e.zip = z.zip
AND e.eno IN (
    SELECT o.eno
    FROM orders o, odetails d, parts p
    WHERE o.ono = d.ono
    AND d.pno = p.pno
    AND p.price > 20);
    
--c
SELECT a.cno, b.cno
FROM customers a, customers b
WHERE a.zip = b.zip;

--d
SELECT c.cname
FROM customers c, orders o, employees e, zipcodes z
WHERE c.cno = o.cno
AND e.zip = z.zip
AND z.city = 'Wichita'
AND e.eno = o.eno;

--e
SELECT c.cname
FROM customers c, orders o, employees e, zipcodes z
WHERE c.cno = o.cno
AND e.zip = z.zip
AND z.city = 'Wichita'
AND e.eno = o.eno
AND c.cno NOT IN (
    SELECT c.cno
    FROM customers c, orders o, employees e, zipcodes z
    WHERE c.cno = o.cno
    AND e.zip = z.zip
    AND z.city != 'Wichita'
    AND e.eno = o.eno);
    
--f
SELECT c.cname
FROM customers c, orders o, odetails d
WHERE c.cno = o.cno
AND o.ono = d.ono
AND d.pno IN (
    SELECT pno
    FROM parts
    WHERE price < 20);
    
--g
SELECT e.ename, sum(p.price)
FROM employees e, orders o, odetails d, parts p
GROUP BY e.ename
HAVING e.eno = o.eno
AND extract(year FROM o.shipped) = 2095
AND o.ono = d.ono
AND d.pno = p.pno;