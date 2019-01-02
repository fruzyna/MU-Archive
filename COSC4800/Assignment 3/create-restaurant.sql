DROP TABLE FOOD_CAT CASCADE CONSTRAINTS;
CREATE TABLE FOOD_CAT (
  FcatName      varchar2(15) primary key
);

DROP TABLE FOOD_ITEM CASCADE CONSTRAINTS;
CREATE TABLE FOOD_ITEM (
  FName         varchar2(15) primary key,
  FI_FC         varchar2(15) not null references FOOD_CAT(FcatName)
);

DROP TABLE CUISINE CASCADE CONSTRAINTS;
CREATE TABLE CUISINE (
  CName         varchar2(10) check (CName IN ('American', 'Mexican', 'Indian', 'Pizza', 'Sandwich', 'Chinese', 'Italian'))
);

DROP TABLE CUST_REVIEW CASCADE CONSTRAINTS;
CREATE TABLE CUST_REVIEW (
  ReviewId      number(10) primary key
);

DROP TABLE RESTAURANT_CUSTOMER CASCADE CONSTRAINTS;
CREATE TABLE RESTAURANT_CUSTOMER (
  Email         varchar2(50) primary key,
  StNo          number(5),
  StrName       varchar(30),
  City          varchar(20),
  State         char(2),
  Zip           number(5),
  Password      varchar2(30),
  Phone         number(10),
  FName         varchar(15),
  MInit         char,
  LName         varchar(15)
);

DROP TABLE RESTAURANT_ORDER CASCADE CONSTRAINTS;
CREATE TABLE RESTAURANT_ORDER (
  ONo           number(10) primary key,
  OrderTime     date,
  PickupTime    date,
  
  OEmail        varchar2(30) references RESTAURANT_CUSTOMER(Email)
);

DROP TABLE RESTAURANT CASCADE CONSTRAINTS;
CREATE TABLE RESTAURANT (
  RestID        number(10) primary key,
  RName         varchar2(30) not null,
  StrNo         number(5),
  StrName       varchar(30),
  City          varchar(20),
  State         char(2),
  Zip           number(5),
  PriceRange    number(1),
  AvgRating     number(2, 1) check(AvgRating >= 1 AND AvgRating <= 5),
  DeliveryFlag  number(1) check(DeliveryFlag >= 0 AND DeliveryFlag <= 1),
  OutdoorFlag   number(1) check(OutdoorFlag >= 0 AND OutdoorFlag <= 1),
  TakeoutFlag   number(1) check(TakeoutFlag >= 0 AND TakeoutFlag <= 1),
  DiningType    varchar(15) check (DiningType IN ('Fast Food', 'Fast Casual', 'Casual Dining', 'Family Style', 'Fine Dining')),
  Hours         varchar2(250),
  Email         varchar2(50),
  Phone         number(10)
);


DROP TABLE REVIEWS CASCADE CONSTRAINTS;
CREATE TABLE REVIEWS (
  Title         varchar2(30),
  ReviewDate    date,
  Rating        number(1) check(Rating >= 1 AND Rating <= 5),
  
  REmail        varchar2(30) references RESTAURANT_CUSTOMER(Email),
  RReviewID     number(10) references CUST_REVIEW(ReviewId),
  RRestId       number(10) references RESTAURANT(RestId),
  unique (RRestId, REmail, ReviewDate)
);

DROP TABLE RESTAURANT_ODETAILS CASCADE CONSTRAINTS;
CREATE TABLE RESTAURANT_ODETAILS (
  Qty           number(2),
  
  DOrder        number(10) references RESTAURANT_ORDER(ONo),
  DRestaurant   number(10) references RESTAURANT(RestId),
  DItem         varchar2(15) references FOOD_ITEM(FName)
);

DROP TABLE MENU CASCADE CONSTRAINTS;
CREATE TABLE MENU (
  MRestaurant   number(10) references RESTAURANT(RestId),
  MItem         varchar2(15) references FOOD_ITEM(FName)
);


DROP TABLE REST_CUISINE CASCADE CONSTRAINTS;
CREATE TABLE REST_CUISINE (
  RCName         varchar2(10),
  CRestId        number(10) references RESTAURANT(RestId)
);