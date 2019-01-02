-- RestaurantAdvisor Database Schema
-- Author : Dr.Praveen Madiraju

DROP TABLE RESTAURANT CASCADE CONSTRAINTS;
CREATE TABLE RESTAURANT (
    RestId     integer primary key,
    RName   varchar2(50) not null,
    Phone   varchar2(12),
    Email   varchar2(50),
    hours   varchar2(50),
    DiningType varchar2(20) check (DiningType in ('Fast Food','Fast Casual','Casual Dining','Family Style', 'Fine Dining')),
    StreetNo   number(5),
    StreetName varchar2(30),
    City    varchar2(15),
    State   varchar2(5),
    Zip     number(10),
    PriceRange varchar2(15),
    DeliveryFlag    char check(DeliveryFlag in ('Y','N')),
    OutdoorSeatingFlag char check(OutdoorSeatingFlag in ('Y','N'))
);

DROP TABLE REST_CUISINE CASCADE CONSTRAINTS;
CREATE TABLE REST_CUISINE (
    RestId     integer references RESTAURANT(RestId),
    CName varchar2(30) check (CNAME in ('American','Mexican','Indian','Pizza','Sandwich','Chinese','Italian')),
    primary key(RestId,CName)
);

DROP TABLE FOOD_ITEM CASCADE CONSTRAINTS;
CREATE TABLE FOOD_ITEM (
    FoodName   varchar2(100) primary key,
    FCatName varchar2(25) check (FCatName in ('Soup', 'Appetizer','Main','Dessert','Drink'))
);


DROP TABLE MENU CASCADE CONSTRAINTS;
CREATE TABLE MENU (
    RestId integer references RESTAURANT(RestId),
    FoodName varchar2(25) references FOOD_ITEM(FoodName),
    Price number(5),
    primary key(RestId,FoodName)
);


DROP TABLE RESTAURANT_CUSTOMER CASCADE CONSTRAINTS;
CREATE TABLE RESTAURANT_CUSTOMER (
    Email   varchar2(40) primary key,
    FName varchar2(30) not null,
    minit char,
    LName varchar2(30) not null,
    Phone   varchar2(12),
    Password    varchar2(30) not null,
    StreetNo   number(10),
    StreetName varchar2(30),
    City    varchar2(15),
    State   varchar2(10),
    Zip     number(5)
);

DROP TABLE RESTAURANT_ORDER CASCADE CONSTRAINTS;
CREATE TABLE RESTAURANT_ORDER (
    Ono  integer primary key,
    OrderDate date not null,
    CustEmail  references RESTAURANT_CUSTOMER(Email),
    OrderDineInFlag    char check(OrderDineInFlag in ('Y','N')),
    OrderDeliveryFlag    char check(OrderDeliveryFlag in ('Y','N')),
    OrderPickupFlag    char check(OrderPickupFlag in ('Y','N')),
    OrderPickupDate date,
    OrderDeliveryDate date,
    OrderDineInReceivedDate date
);

DROP TABLE RESTAURANT_ODETAILS CASCADE CONSTRAINTS;
CREATE TABLE RESTAURANT_ODETAILS (
    Ono  integer references RESTAURANT_ORDER(Ono),
    FoodName varchar2(25) references FOOD_ITEM(FoodName),
    RestId integer references RESTAURANT(RestId),
    Qty number(5) not null check (Qty > 0),
    primary key(Ono,FoodName,RestId)    
);

DROP TABLE CUST_REVIEW CASCADE CONSTRAINTS;
CREATE TABLE CUST_REVIEW (
    ReviewId integer primary key,
    CustEmail varchar2(40) references RESTAURANT_CUSTOMER(Email),
    RestId integer references RESTAURANT(RestId),
    ReviewTitle varchar2(100),
    ReviewDescr varchar2(1000),
    ReviewDate date,
    Rating  number(2) check (Rating >= 1 and Rating <= 5),    
    unique (RestId,CustEmail,ReviewDate)
);

DROP SEQUENCE Restaurant_RestId_Seq;
CREATE SEQUENCE Restaurant_RestId_Seq START with 1000 INCREMENT BY 1;

DROP SEQUENCE Customer_ReviewId_Seq;
CREATE SEQUENCE Customer_ReviewId_Seq START with 12000 INCREMENT BY 1;

DROP SEQUENCE Restaurant_Order_Ono;
CREATE SEQUENCE Restaurant_Order_Ono START with 27000 INCREMENT BY 1;


insert into RESTAURANT VALUES (Restaurant_RestId_Seq.nextval,'China Town', '111-111-1111',null,null,'Family Style',null,null,null,null,null,'5-20','Y','N');
insert into RESTAURANT VALUES (Restaurant_RestId_Seq.nextval,'Milwaukee Burger', '112-111-1111',null,null,'Casual Dining',null,null,null,null,null,'5-15','Y','Y');
insert into RESTAURANT VALUES (Restaurant_RestId_Seq.nextval,'Indian Mahal', '113-111-1111',null,null,'Family Style',null,null,null,null,null,'8-20','Y','N');
insert into RESTAURANT VALUES (Restaurant_RestId_Seq.nextval,'Olive Garden', '114-111-1111',null,null,'Family Style',null,null,null,null,null,'10-20','Y','Y');

insert into REST_CUISINE VALUES (1000,'Chinese');
insert into REST_CUISINE VALUES (1001,'American');
insert into REST_CUISINE VALUES (1001,'Sandwich');
insert into REST_CUISINE VALUES (1002,'Indian');
insert into REST_CUISINE VALUES (1003,'Italian');
insert into REST_CUISINE VALUES (1003,'American');
insert into REST_CUISINE VALUES (1003,'Sandwich');

insert into FOOD_ITEM VALUES ('Chicken Burger','Main');
insert into FOOD_ITEM VALUES ('Veggie Burger','Main');
insert into FOOD_ITEM VALUES ('Veggie Pasta','Main');
insert into FOOD_ITEM VALUES ('Chicken Pasta','Main');
insert into FOOD_ITEM VALUES ('Chicken Sandwich','Main');
insert into FOOD_ITEM VALUES ('Veggie Sandwich','Main');
insert into FOOD_ITEM VALUES ('Tomato Mozarella Sandwich','Main');
insert into FOOD_ITEM VALUES ('Chicken Lo Mein Noodles','Main');
insert into FOOD_ITEM VALUES ('Veggie Pan Fried Noodles','Main');
insert into FOOD_ITEM VALUES ('Paneer Tikka Masala Curry','Main');
insert into FOOD_ITEM VALUES ('Chicken Tikka Masala Curry','Main');

insert into FOOD_ITEM VALUES ('Cesear Salad','Appetizer');
insert into FOOD_ITEM VALUES ('Chicken Salad','Appetizer');
insert into FOOD_ITEM VALUES ('French Fries','Appetizer');
insert into FOOD_ITEM VALUES ('Cheese Curds','Appetizer');
insert into FOOD_ITEM VALUES ('Chicken Wings','Appetizer');
insert into FOOD_ITEM VALUES ('Nacho Chips','Appetizer');
insert into FOOD_ITEM VALUES ('Edamame Beans','Appetizer');
insert into FOOD_ITEM VALUES ('Spring Rolls','Appetizer');

insert into FOOD_ITEM VALUES ('Chicken Soup','Soup');
insert into FOOD_ITEM VALUES ('Bean Soup','Soup');
insert into FOOD_ITEM VALUES ('Veggie Soup','Soup');
insert into FOOD_ITEM VALUES ('Clam Chowder Soup','Soup');
insert into FOOD_ITEM VALUES ('Minestrone Soup','Soup');
insert into FOOD_ITEM VALUES ('Hot and Sour Soup','Soup');

insert into MENU VALUES (1000,'Edamame Beans',3.50);
insert into MENU VALUES (1000,'Chicken Lo Mein Noodles',14.50);
insert into MENU VALUES (1000,'Hot and Sour Soup',3.50);

insert into MENU VALUES (1001,'Chicken Soup',3.70);
insert into MENU VALUES (1001,'Bean Soup',3.25);
insert into MENU VALUES (1001,'Cheese Curds',4.20);
insert into MENU VALUES (1001,'Chicken Wings',4.50);
insert into MENU VALUES (1001,'Chicken Sandwich',10.50);
insert into MENU VALUES (1001,'Veggie Sandwich',10.00);
insert into MENU VALUES (1001,'Chicken Burger',14.00);
insert into MENU VALUES (1001,'Veggie Burger',12.00);

insert into RESTAURANT_CUSTOMER(Email,FName,LName,Password) values('john@restaurantadvisor.com','John','Smith','john123');
insert into RESTAURANT_CUSTOMER(Email,FName,LName,Password) values('franklin@restaurantadvisor.com','Franklin','Wong','franklin123');
insert into RESTAURANT_CUSTOMER(Email,FName,LName,Password) values('alicia@restaurantadvisor.com','Alicia','Zelaya','alicia123');
insert into RESTAURANT_CUSTOMER(Email,FName,LName,Password) values('jennifer@restaurantadvisor.com','Jennifer','Wallace','jennifer123');
insert into RESTAURANT_CUSTOMER(Email,FName,LName,Password) values('ramesh@restaurantadvisor.com','Ramesh','Narayan','ramesh123');
insert into RESTAURANT_CUSTOMER(Email,FName,LName,Password) values('james@restaurantadvisor.com','James','Borg','james123');

insert into RESTAURANT_ORDER VALUES (Restaurant_Order_Ono.nextval,sysdate,'john@restaurantadvisor.com','Y','N','N',null,null,sysdate + interval '10' minute);
insert into RESTAURANT_ORDER VALUES (Restaurant_Order_Ono.nextval,sysdate,'franklin@restaurantadvisor.com','N','Y','N',null,sysdate + interval '45' minute,null);

insert into RESTAURANT_ODETAILS VALUES (27000,'Chicken Soup','1001',1);
insert into RESTAURANT_ODETAILS VALUES (27000,'Chicken Sandwich','1001',2);
insert into RESTAURANT_ODETAILS VALUES (27000,'Veggie Burger','1001',1);

insert into RESTAURANT_ODETAILS VALUES (27001,'Edamame Beans','1000',2);
insert into RESTAURANT_ODETAILS VALUES (27001,'Hot and Sour Soup','1000',3);
insert into RESTAURANT_ODETAILS VALUES (27001,'Chicken Lo Mein Noodles','1000',3);

insert into CUST_REVIEW VALUES(Customer_ReviewId_Seq.nextval,'franklin@restaurantadvisor.com',1000,'Yummy Food','The noodles are a must try here. Awesome food.',sysdate,4.5);
insert into CUST_REVIEW VALUES(Customer_ReviewId_Seq.nextval,'franklin@restaurantadvisor.com',1003,'Service Not Great','service was poor. We had to wait a long time',sysdate,1.5);
insert into CUST_REVIEW VALUES(Customer_ReviewId_Seq.nextval,'john@restaurantadvisor.com',1001,'Lovely','Satisfying Food',sysdate,5);


--modifications for testing
INSERT INTO RESTAURANT VALUES
  (Restaurant_RestId_Seq.nextval, 'Qudoba', '414-431-0099', null, '10:30AM to 10PM-3AM', 'Fast Food', 803, 'N 16 St', 'Milwaukee', 'WI', 53233, '5-15', 'N', 'N');
INSERT INTO RESTAURANT VALUES
  (Restaurant_RestId_Seq.nextval, 'Einstein Bros. Bagles', '4142880469', null, '8AM to 4/7PM', 'Fast Food', 1441, 'Wisconsin Ave', 'Milwaukee', 'WI', 53233, '5-10', 'N', 'N');
INSERT INTO RESTAURANT VALUES
  (Restaurant_RestId_Seq.nextval, 'Papa Johns Pizza', '414-342-7272', null, '10AM to 1/3AM', 'Fast Food', 1611, 'W Wells St', 'Milwaukee', 'WI', 53233, '5-20', 'Y', 'N');
INSERT INTO RESTAURANT VALUES
  (Restaurant_RestId_Seq.nextval, 'Sobelmans', '414-933-1601', null, '11AM to 9/10PM', 'Casual Dining', 1601, 'W Wells St', 'Milwaukee', 'WI', 53233, '10-25', 'N', 'Y');
INSERT INTO RESTAURANT VALUES
  (Restaurant_RestId_Seq.nextval, 'Jimmy Johns', '414-344-1234', null, '10AM to 3AM', 'Fast Food', 1532, 'W Wells St', 'Milwaukee', 'WI', 53233, '10-25', 'Y', 'N');
INSERT INTO RESTAURANT VALUES
  (Restaurant_RestId_Seq.nextval, 'Dog Haus', '414-933-9179', null, '11AM to 12/2AM', 'Fast Food', 1633, 'W Wells St', 'Milwaukee', 'WI', 53233, '5-15', 'N', 'N');
INSERT INTO RESTAURANT VALUES
  (Restaurant_RestId_Seq.nextval, 'Real Chili', '414-342-6955', null, '11AM to 11PM-3AM', 'Fast Food', 1625, 'W Wells St', 'Milwaukee', 'WI', 53233, '5-15', 'N', 'N');
INSERT INTO RESTAURANT VALUES
  (Restaurant_RestId_Seq.nextval, 'Cousins Subs', '414-277-7007', null, '10AM to 9/10PM', 'Fast Food', 1612, 'W Wisconsin Ave', 'Milwaukee', 'WI', 53233, '5-15', 'Y', 'N');
INSERT INTO RESTAURANT VALUES
  (Restaurant_RestId_Seq.nextval, 'Pita Brothers', '414-539-4094', null, '10:30AM to 9PM', 'Fast Food', 1614, 'W Wisconsin Ave', 'Milwaukee', 'WI', 53233, '5-15', 'N', 'N');
INSERT INTO RESTAURANT VALUES
  (Restaurant_RestId_Seq.nextval, 'Burger King', '414-344-9840', null, '6/7AM to 10PM/12AM', 'Fast Food', 1400, 'W Wells St', 'Milwaukee', 'WI', 53233, '5-15', 'N', 'N');
  
insert into REST_CUISINE VALUES (1004,'Mexican');
insert into REST_CUISINE VALUES (1005,'American');
insert into REST_CUISINE VALUES (1006,'Pizza');
insert into REST_CUISINE VALUES (1007,'American');
insert into REST_CUISINE VALUES (1008,'Sandwich');
insert into REST_CUISINE VALUES (1009,'American');
insert into REST_CUISINE VALUES (1010,'American');
insert into REST_CUISINE VALUES (1011,'Sandwich');
insert into REST_CUISINE VALUES (1012,'Sandwich');
insert into REST_CUISINE VALUES (1013,'American');

insert into FOOD_ITEM VALUES ('Tacos','Main');
insert into FOOD_ITEM VALUES ('Burrito','Main');
insert into FOOD_ITEM VALUES ('Chips','Appetizer');
insert into FOOD_ITEM VALUES ('Queso','Appetizer');

insert into MENU VALUES (1004,'Tacos',7.00);
insert into MENU VALUES (1004,'Burrito',8.50);
insert into MENU VALUES (1004,'Chips',3.50);
insert into MENU VALUES (1004,'Queso',0.50);

insert into FOOD_ITEM VALUES ('Bagle','Main');
insert into FOOD_ITEM VALUES ('Cream Cheese','Appetizer');

insert into MENU VALUES (1005,'Bagle',3.50);
insert into MENU VALUES (1005,'Cream Cheese',0.50);

insert into FOOD_ITEM VALUES ('Medium Pizza','Main');
insert into FOOD_ITEM VALUES ('Large Pizza','Main');
insert into FOOD_ITEM VALUES ('Garlic Bread Sticks','Appetizer');
insert into FOOD_ITEM VALUES ('Garlic Cheddar Knots','Appetizer');

insert into MENU VALUES (1006,'Medium Pizza',10.00);
insert into MENU VALUES (1006,'Large Pizza',13.00);
insert into MENU VALUES (1006,'Garlic Bread Sticks',5.50);
insert into MENU VALUES (1006,'Garlic Cheddar Knots',5.00);