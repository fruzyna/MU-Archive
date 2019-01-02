--Liam Fruzyna
--MU COSC 4800
--Final Exam Q2

--a
SELECT anum, buildingnum, floornum
FROM apartment
WHERE available like 'yes';

--b
SELECT c.cname
FROM client c
WHERE c.cid NOT IN (SELECT r.cid FROM rentedby r);

--c
SELECT c.cid, c.cname, c.ssn, c.phone, count(*)
FROM client c, rentedby r
WHERE c.cid = r.cid AND
    r.startrent > to_date('1-JAN-14','DD-MON-YY')
GROUP BY c.cid, c.cname, c.ssn, c.phone
HAVING count(*) > 1;

--d
SELECT c.cname
FROM rentedby r, client c
WHERE c.cid = r.cid
GROUP BY c.cname
HAVING min(r.rent) >= 1000;

--e
SELECT avg(rent)
FROM rentedby;

--f (given)
SELECT R.ANUM, COUNT(*)
FROM RENTEDBY R
GROUP BY R.ANUM
HAVING COUNT(*) > 1;

--f
SELECT distinct r.anum, (
    SELECT COUNT(*)
    FROM rentedby 
    WHERE r.anum = anum)
FROM rentedby r
WHERE (SELECT COUNT(*) FROM rentedby WHERE  r.anum = anum) > 1;