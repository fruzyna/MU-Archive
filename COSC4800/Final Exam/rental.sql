--Liam Fruzyna
--MU COSC 4800
--Final Exam Q1

DROP TABLE client CASCADE CONSTRAINTS;
CREATE TABLE client (
  CId    varchar2(15) primary key not null,
  CName    varchar2(20) not null,
  SSN   char(9) not null,
  Phone    varchar2(12) not null
);

DROP TABLE apartment CASCADE CONSTRAINTS;

CREATE TABLE apartment (
  ANum    varchar2(4) primary key not null,
  AType    char(1) not null,
  NumRoom    number not null,
  BuildingNum    number not null,
  FloorNum    number not null,
  Available varchar2(3) not null check (Available in ('yes', 'no'))
);


DROP TABLE rentedby CASCADE CONSTRAINTS;
CREATE TABLE rentedby (

  CId    varchar2(15) not null,
  ANum varchar2(4) not null,
  foreign key(ANum) references apartment(anum) ,
  foreign key (CId) references client(CId),
  StartRent date not null,
  Rent number not null,
  PeriodRent varchar2(5) not null check (PeriodRent > 0 and PeriodRent <= 12),
  Deposit number not null,
  Balance number not null,
  primary key(Cid,ANum,StartRent)
);

INSERT INTO client VALUES ('100', 'Johnson, John', '666666666', '414-555-5555');
INSERT INTO client VALUES ('101', 'Johnson, Joe', '644446666', '414-444-5555');
INSERT INTO client VALUES ('102', 'Nickson, Nick', '123123123', '414-123-5555');
INSERT INTO client VALUES ('103', 'Jackson, Jack', '321321321', '414-321-5555');
INSERT INTO client VALUES ('104', 'Joestar, Joe', '222222222', '414-222-5555');
INSERT INTO client VALUES ('105', 'Mickson, Mick', '333333333', '414-333-5555');
INSERT INTO client VALUES ('106', 'Smith, John', '444444444', '414-678-0000');
INSERT INTO client VALUES ('107', 'Smith, Sheryl', '555555555', '414-578-0200');
  
INSERT INTO APARTMENT VALUES ('1000', 'A', '1', '10' , '1', 'no');
INSERT INTO APARTMENT VALUES ('1001', 'B', '2', '11' , '2', 'no');  
INSERT INTO APARTMENT VALUES ('1002', 'C', '2', '12' , '2', 'no');
INSERT INTO APARTMENT VALUES ('1003', 'B', '2', '11' , '2', 'no');    
INSERT INTO APARTMENT VALUES ('1004', 'C', '1', '10' , '1', 'no');
INSERT INTO APARTMENT VALUES ('1005', 'A', '2', '13' , '3', 'no');  
INSERT INTO APARTMENT VALUES ('1006', 'A', '2', '13' , '3', 'yes');  
INSERT INTO APARTMENT VALUES ('1007', 'D', '1', '11' , '1', 'yes');  



INSERT INTO RENTEDBY VALUES ('100', '1000', '08-DEC-2015', 1055, 12, 1000, 0);
INSERT INTO RENTEDBY VALUES ('100', '1001', '15-APR-2016', 1155, 9, 1000, 0);
INSERT INTO RENTEDBY VALUES ('101', '1001', '07-AUG-2016', 1250, 6, 1000, 20);
INSERT INTO RENTEDBY VALUES ('102', '1002', '06-FEB-2016', 955, 12, 1000, 400);
INSERT INTO RENTEDBY VALUES ('103', '1003', '05-MAR-2016', 965, 9, 1000, 300);
INSERT INTO RENTEDBY VALUES ('104', '1004', '07-OCT-2016', 905, 3, 1000, 0);
INSERT INTO RENTEDBY VALUES ('105', '1005', '08-JUN-2016', 1065, 12, 1000, 200);
INSERT INTO RENTEDBY VALUES ('105', '1003', '08-FEB-2016', 965, 12, 1000, 200);

commit;
