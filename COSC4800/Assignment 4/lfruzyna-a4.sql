--Liam Fruzyna and Katy Weathington
--COSC 4800
--Assignment 4

--1
SELECT rname
FROM restaurant
WHERE diningtype = 'Fine Dining' and
    deliveryflag =  'Y';

--2  
SELECT r.phone
FROM restaurant r, rest_cuisine c
WHERE r.deliveryflag = 'Y' and
    r.restid = c.restid and
    c.cname = 'American';

--3
SELECT rname
FROM restaurant
WHERE restid NOT IN (
    SELECT restid
    FROM menu);
    
--4
SELECT c.fname, count(r.custemail)
FROM restaurant_customer c, cust_review r
WHERE c.email = r.custemail
GROUP BY c.fname;

--5
SELECT rname
FROM restaurant
WHERE restid IN (
    SELECT restid
    FROM menu
    WHERE price = (
        SELECT MAX(price)
        FROM menu));
        
--6
SELECT r.restid, r.rname, avg(c.rating)
FROM restaurant r, cust_review c
WHERE r.restid = c.restid AND
    3 < (
        SELECT AVG(rating)
        FROM cust_review
        WHERE restid = c.restid)
GROUP BY r.restid, r.rname;
    
--7
SELECT r.rname
FROM restaurant r
WHERE 2 <= (
    SELECT COUNT(c.restid)
    FROM rest_cuisine c
    WHERE r.restid = c.restid);
        
--8 
SELECT *
FROM (
    SELECT c.fname, COUNT(r.custemail) as reviews
    FROM restaurant_customer c, cust_review r
    WHERE r.custemail = c.email
    GROUP BY c.fname
    ORDER BY reviews DESC)
WHERE ROWNUM = 1;

--9
SELECT c.fname
FROM restaurant_customer c, restaurant_order o
WHERE c.email = o.custemail AND
    o.orderpickupflag = 'Y' AND
    o.orderpickupdate IS NULL;
    
--10
SELECT r.RName, AVG(24 * (o.OrderDeliveryDate-o.OrderDate))
FROM restaurant_order o, restaurant_odetails od, restaurant r
WHERE o.ono=od.ono AND
    od.restID=r.restID AND
    o.OrderDeliveryFlag = 'Y'
GROUP BY r.RName;


--11
SELECT r.rname, count(o.restid)
FROM restaurant r, restaurant_odetails o
WHERE r.restid = o.restid
GROUP BY r.rname;

--12
SELECT fname
FROM restaurant_customer
WHERE email NOT IN (
    SELECT r.custemail
    FROM cust_review r
    WHERE r.rating NOT IN (
        SELECT MIN(rating)
        FROM cust_review
        WHERE restid = r.restid
        GROUP BY restid)) AND
    email IN (
        SELECT custemail
        FROM cust_review);

--13
SELECT fname
FROM restaurant_customer
WHERE phone LIKE '414%';

--14
SELECT c.fname, SUM(m.price)
FROM restaurant_customer c, restaurant_odetails d, restaurant_order o, menu m
WHERE c.email = o.custemail AND
    o.ono = d.ono AND
    d.foodname = m.foodname
GROUP BY c.fname;

--15
SELECT fname
FROM restaurant_customer
WHERE email IN (
    SELECT o.custemail
    FROM restaurant_order o, restaurant_odetails d, restaurant r, rest_cuisine c
    WHERE o.ono = d.ono AND
        d.restid = r.restid AND
        r.restid = c.restid AND
        c.cname = 'Indian') AND
    email NOT IN (
        SELECT o.custemail
        FROM restaurant_order o, restaurant_odetails d, restaurant r, rest_cuisine c
        WHERE o.ono = d.ono AND
            d.restid = r.restid AND
            r.restid = c.restid AND
            c.cname != 'Indian');