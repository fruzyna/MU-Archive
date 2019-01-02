--10
SELECT lname
FROM employee
WHERE ssn NOT IN (SELECT essn
                    FROM dependent)
            AND ssn IN (SELECT mgrssn
                        FROM department);
--9

--8                      
SELECT AVG(salary)
FROM employee
WHERE SEX = 'F';

--7
SELECT d.dname, AVG(e.salary)
FROM department d, employee e
WHERE e.dno = d.dnumber
GROUP BY d.dname;

--6
SELECT fname, lname
FROM employee
WHERE ssn NOT IN (SELECT essn
                    FROM works_on);
    
--5                
SELECT e.fname, e.lname
FROM employee e, works_on w, project p
WHERE e.ssn = w.essn AND COUNT(w.essn) = COUNT(p.pnumber)
GROUP BY e.fname, e.lname;

--4
SELECT p.pname, SUM(w.hours)
FROM works_on w, project p
WHERE w.pno = p.pnumber
GROUP BY p.pname;

--3
select fname, lname
from employee
where superssn = (select ssn from employee where fname = 'Franklin' AND lname = 'Wong');

--2
select e.fname, e.lname
from employee e, dependent d
where e.fname = d.dependent_name and e.ssn = d.essn;

--1
select ssn, fname, lname, p.pnumber, p.pname
from employee e, works_on w, project p
where e.ssn = w.essn and w.hours > 10 and e.dno = 5 and w.pno = p.pnumber and p.pname = 'ProductX';