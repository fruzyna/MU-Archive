DROP SEQUENCE restaurant_seq;
CREATE SEQUENCE restaurant_seq
 START WITH     1000
 INCREMENT BY   1;
 
DROP SEQUENCE review_seq;
CREATE SEQUENCE review_seq
 START WITH     1000
 INCREMENT BY   1;
 
DROP SEQUENCE order_seq;
CREATE SEQUENCE order_seq
 START WITH     1000
 INCREMENT BY   1;
 
INSERT INTO CUISINE VALUES ('American');
INSERT INTO CUISINE VALUES ('Mexican');
INSERT INTO CUISINE VALUES ('Indian');
INSERT INTO CUISINE VALUES ('Pizza');
INSERT INTO CUISINE VALUES ('Sandwich');
INSERT INTO CUISINE VALUES ('Chinese');
INSERT INTO CUISINE VALUES ('Italian');

-- An example date insertion using to_date
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('liamfruzyna@gmail.com', 1500, 'W Wells St Apt 280', 'Milwaukee', 'WI', 53233, 'Password', 6306776675, 'Liam', 'M', 'Fruzyna');
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('ellenmariebakke@gmail.com', 2113, 'W Wisconsin Ave Apt 11', 'Milwaukee', 'WI', 53233, '123456', 6128894102, 'Ellen', 'M', 'Bakke');
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('sebastien.feren@marquette.edu', 1500, 'W Wells St Apt 280', 'Milwaukee', 'WI', 53233, 'abcdef', 6306776675, 'Sebastien', 'M', 'Feren');
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('hasan.barakat@marquette.edu', 1500, 'W Wells St Apt 280', 'Milwaukee', 'WI', 53233, 'abc123', 6306776675, 'Liam', 'M', 'Fruzyna');
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('103megan@gmail.com', 103, 'W Noyes St', 'Arlington Heights', 'IL', 60005, 'jmleadf', 6306770384, 'Kathleen', 'M', 'Fruzyna');
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('103fuzzy@gmail.com', 103, 'W Noyes St', 'Arlington Heights', 'IL', 60005, 'db4379008', 6306770383, 'Joseph', 'P', 'Fruzyna');
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('erinfruzyna99@gmail.com', 103, 'W Noyes St', 'Arlington Heights', 'IL', 60005, 'ioenoboi', 6302906211, 'Erin', 'C', 'Fruzyna');
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('aidanfuzzy@gmail.com', 103, 'W Noyes St', 'Arlington Heights', 'IL', 60005, 'Password', 6307700014, 'Aidan', 'P', 'Fruzyna');
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('declanfruzyna@gmail.com', 103, 'W Noyes St', 'Arlington Heights', 'IL', 60005, 'Password', 2245090039, 'Kathleen', 'M', 'Fruzyna');
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('potus@whitehouse.gov', 1600, 'Pennsylvania Ave', 'Washington', 'DC', 20500, 'qwertyuiop', 2024561111, 'Donald', 'J', 'Trump');
INSERT INTO RESTAURANT_CUSTOMER VALUES 
  ('praveen.madiraju@marquette.edu', 1313, 'W Wisconsin Ave', 'Milwaukee', 'WI', 53233, 'asdfghjkl', 4142886340, 'Praveen', 'Z', 'Madiraju');
  
INSERT INTO RESTAURANT VALUES
  (restaurant_seq.nextval, 'Qudoba', 803, 'N 16 St', 'Milwaukee', 'WI', 53233, 1, 3.8, 0, 0, 1, 'Fast Food', 'Saturday	10:30AM–3AM
Sunday	10:30AM–10PM
Monday	10:30AM–11PM
Tuesday	10:30AM–11PM
Wednesday	10:30AM–11PM
Thursday	10:30AM–2:30AM
Friday	10:30AM–3AM', 'none', 4144310099);
INSERT INTO RESTAURANT VALUES
  (restaurant_seq.nextval, 'Einstein Bros. Bagles', 1441, 'Wisconsin Ave', 'Milwaukee', 'WI', 53233, 1, null, 0, 0, 1, 'Fast Food', 'Sunday Closed
Monday 8AM–7PM
Tuesday 8AM–7PM
Wednesday 8AM–7PM
Thursday 8AM–7PM
Friday 8AM–4PM
Saturday Closed', 'none', 4142880469);
INSERT INTO RESTAURANT VALUES
  (restaurant_seq.nextval, 'Papa Johns Pizza', 1611, 'W Wells St', 'Milwaukee', 'WI', 53233, 1, 3.5, 1, 0, 1, 'Fast Food', 'Saturday	10AM–3AM
Sunday	10AM–1AM
Monday	10AM–1AM
Tuesday	10AM–1AM
Wednesday	10AM–1AM
Thursday	10AM–1AM
Friday	10AM–3AM', 'none', 4143427272);
INSERT INTO RESTAURANT VALUES
  (restaurant_seq.nextval, 'Sobelmans', 1601, 'W Wells St', 'Milwaukee', 'WI', 53233, 2, 4.2, 0, 1, 1, 'Casual Dining', 'Saturday	11AM–10PM
Sunday	11AM–9PM
Monday	11AM–10PM
Tuesday	11AM–10PM
Wednesday	11AM–10PM
Thursday	11AM–10PM
Friday	11AM–10PM', 'none', 4149331601);
INSERT INTO RESTAURANT VALUES
  (restaurant_seq.nextval, 'Jimmy Johns', 1532, 'W Wells St', 'Milwaukee', 'WI', 53233, 1, 3.2, 1, 0, 1, 'Fast Food', 'Saturday	10AM–3AM
Sunday	10AM–3AM
Monday	10AM–3AM
Tuesday	10AM–3AM
Wednesday	10AM–3AM
Thursday	10AM–3AM
Friday	10AM–3AM', 'none', 4143441234);
INSERT INTO RESTAURANT VALUES
  (restaurant_seq.nextval, 'Dog Haus', 1633, 'W Wells St', 'Milwaukee', 'WI', 53233, 1, 3.9, 0, 0, 1, 'Fast Food', 'Saturday	11AM–2AM
Sunday	11AM–12AM
Monday	11AM–12AM
Tuesday	11AM–12AM
Wednesday	11AM–2AM
Thursday	11AM–2AM
Friday	11AM–2AM', 'none', 4149339179);
INSERT INTO RESTAURANT VALUES
  (restaurant_seq.nextval, 'Real Chili', 1625, 'W Wells St', 'Milwaukee', 'WI', 53233, 1, 4.4, 0, 0, 1, 'Fast Food', 'Saturday	11AM–3AM
Sunday	11AM–11PM
Monday	11AM–2AM
Tuesday	11AM–2AM
Wednesday	11AM–2AM
Thursday	11AM–2AM
Friday	11AM–3AM', 'none', 4143426955);
INSERT INTO RESTAURANT VALUES
  (restaurant_seq.nextval, 'Cousins Subs', 1612, 'W Wisconsin Ave', 'Milwaukee', 'WI', 53233, 1, 3.2, 1, 0, 1, 'Fast Food', 'Saturday	10AM–10PM
Sunday	10AM–9PM
Monday	10AM–10PM
Tuesday	10AM–10PM
Wednesday	10AM–10PM
Thursday	10AM–10PM
Friday	10AM–10PM', 'none', 4142777007);
INSERT INTO RESTAURANT VALUES
  (restaurant_seq.nextval, 'Pita Brothers', 1614, 'W Wisconsin Ave', 'Milwaukee', 'WI', 53233, 1, 4.9, 0, 0, 1, 'Fast Food', 'Saturday	11AM–9AM
Sunday	Closed
Monday	10:30AM–9PM
Tuesday	10:30AM–9PM
Wednesday	10:30AM–9PM
Thursday	10:30AM–9PM
Friday	10:30AM–9PM', 'none', 4145394094);
INSERT INTO RESTAURANT VALUES
  (restaurant_seq.nextval, 'Burger King', 1400, 'W Wells St', 'Milwaukee', 'WI', 53233, 1, 2.8, 0, 0, 1, 'Fast Food', 'Saturday	6AM–12PM
Sunday	7AM–10PM
Monday	6AM–10PM
Tuesday	6AM–10PM
Wednesday	6AM–10PM
Thursday	6AM–10PM
Friday	6AM–12PM', 'none', 4143449840);

INSERT INTO REVIEWS VALUES
  ('This food sticks', to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), 2, 'liamfruzyna@gmail.com', review_seq.nextval, 1000);
INSERT INTO REVIEWS VALUES
  ('This food sticks good', to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), 4, 'ellenmariebakke@gmail.com', review_seq.nextval, 1001);
INSERT INTO REVIEWS VALUES
  ('This food is great', to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), 5, 'hasan.barakat@marquette.edu', review_seq.nextval, 1002);
INSERT INTO REVIEWS VALUES
  ('This food is bad', to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), 2, 'sebastien.feren@marquette.edu', review_seq.nextval, 1003);
INSERT INTO REVIEWS VALUES
  ('This food tastes like food', to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), 3, 'praveen.madiraju@marquette.edu', review_seq.nextval, 1004);
INSERT INTO REVIEWS VALUES
  ('This food didnt exist', to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), 1, '103megan@gmail.com', review_seq.nextval, 1005);
INSERT INTO REVIEWS VALUES
  ('This food is sticks', to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), 3, '103fuzzyn@gmail.com', review_seq.nextval, 1006);
INSERT INTO REVIEWS VALUES
  ('This food is free', to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), 4, 'erinfruzyna99@gmail.com', review_seq.nextval, 1007);
INSERT INTO REVIEWS VALUES
  ('This food is pink', to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), 2, 'aidanfuzzy@gmail.com', review_seq.nextval, 1008);
INSERT INTO REVIEWS VALUES
  ('This food is average', to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), 3, 'declanfruzyna@gmail.com', review_seq.nextval, 1009);

INSERT INTO CUST_REVIEW VALUES
  (1000);
INSERT INTO CUST_REVIEW VALUES
  (1001);
INSERT INTO CUST_REVIEW VALUES
  (1002);
INSERT INTO CUST_REVIEW VALUES
  (1003);
INSERT INTO CUST_REVIEW VALUES
  (1004);
INSERT INTO CUST_REVIEW VALUES
  (1005);
INSERT INTO CUST_REVIEW VALUES
  (1006);
INSERT INTO CUST_REVIEW VALUES
  (1007);
INSERT INTO CUST_REVIEW VALUES
  (1008);
INSERT INTO CUST_REVIEW VALUES
  (1009);

INSERT INTO FOOD_CAT VALUES
  ('Carbs');
INSERT INTO FOOD_CAT VALUES
  ('Veggies');
INSERT INTO FOOD_CAT VALUES
  ('Sugars');
INSERT INTO FOOD_CAT VALUES
  ('Proteins');
INSERT INTO FOOD_CAT VALUES
  ('Dairy');
INSERT INTO FOOD_CAT VALUES
  ('Fruits');
INSERT INTO FOOD_CAT VALUES
  ('Not Food');
INSERT INTO FOOD_CAT VALUES
  ('Multi');
INSERT INTO FOOD_CAT VALUES
  ('Air');

INSERT INTO FOOD_ITEM VALUES
  ('Pizza', 'Multi');
INSERT INTO FOOD_ITEM VALUES
  ('Wings', 'Proteins');
INSERT INTO FOOD_ITEM VALUES
  ('French Fries', 'Veggies');
INSERT INTO FOOD_ITEM VALUES
  ('Apple', 'Fruits');
INSERT INTO FOOD_ITEM VALUES
  ('Sandwhich', 'Multi');
INSERT INTO FOOD_ITEM VALUES
  ('Bread', 'Carbs');
INSERT INTO FOOD_ITEM VALUES
  ('Air', 'Air');
INSERT INTO FOOD_ITEM VALUES
  ('Grass', 'Veggies');
INSERT INTO FOOD_ITEM VALUES
  ('Water', 'Not Food');
INSERT INTO FOOD_ITEM VALUES
  ('Pasta', 'Carbs');

INSERT INTO MENU VALUES
  (1000, 'Pizza');
INSERT INTO MENU VALUES
  (1001, 'Pizza');
INSERT INTO MENU VALUES
  (1002, 'Pizza');
INSERT INTO MENU VALUES
  (1003, 'Pizza');
INSERT INTO MENU VALUES
  (1004, 'Pizza');
INSERT INTO MENU VALUES
  (1005, 'Pizza');
INSERT INTO MENU VALUES
  (1006, 'Pizza');
INSERT INTO MENU VALUES
  (1007, 'Pizza');
INSERT INTO MENU VALUES
  (1008, 'Pizza');
INSERT INTO MENU VALUES
  (1009, 'Pizza');
INSERT INTO MENU VALUES
  (1000, 'Apple');
INSERT INTO MENU VALUES
  (1001, 'Apple');
INSERT INTO MENU VALUES
  (1002, 'Apple');
INSERT INTO MENU VALUES
  (1003, 'Apple');
INSERT INTO MENU VALUES
  (1004, 'Apple');
INSERT INTO MENU VALUES
  (1005, 'Apple');
INSERT INTO MENU VALUES
  (1006, 'Apple');
INSERT INTO MENU VALUES
  (1007, 'Apple');
INSERT INTO MENU VALUES
  (1008, 'Apple');
INSERT INTO MENU VALUES
  (1009, 'Apple');

INSERT INTO RESTAURANT_ODETAILS VALUES
  (2, 1001, 1009, 'Pizza');
INSERT INTO RESTAURANT_ODETAILS VALUES
  (3, 1002, 1008, 'Pizza');
INSERT INTO RESTAURANT_ODETAILS VALUES
  (4, 1003, 1007, 'Pizza');
INSERT INTO RESTAURANT_ODETAILS VALUES
  (1, 1004, 1006, 'Pizza');
INSERT INTO RESTAURANT_ODETAILS VALUES
  (2, 1000, 1005, 'Pizza');
INSERT INTO RESTAURANT_ODETAILS VALUES
  (3, 1005, 1004, 'Pizza');
INSERT INTO RESTAURANT_ODETAILS VALUES
  (4, 1006, 1003, 'Pizza');
INSERT INTO RESTAURANT_ODETAILS VALUES
  (1, 1007, 1002, 'Pizza');
INSERT INTO RESTAURANT_ODETAILS VALUES
  (1, 1008, 1001, 'Pizza');
INSERT INTO RESTAURANT_ODETAILS VALUES
  (1, 1009, 1000, 'Pizza');

INSERT INTO RESTAURANT_ORDER VALUES
  (1000, to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), to_date('1927/11/10:05:30:40AM', 'yyyy/mm/dd:hh:mi:ssam'), 'liamfruzyna@gmail.com');
INSERT INTO RESTAURANT_ORDER VALUES
  (1001, to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), to_date('1927/11/10:05:30:40AM', 'yyyy/mm/dd:hh:mi:ssam'), '103megan@gmail.com');
INSERT INTO RESTAURANT_ORDER VALUES
  (1002, to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), to_date('1927/11/10:05:30:40AM', 'yyyy/mm/dd:hh:mi:ssam'), '103fuzzy@gmail.com');
INSERT INTO RESTAURANT_ORDER VALUES
  (1003, to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), to_date('1927/11/10:05:30:40AM', 'yyyy/mm/dd:hh:mi:ssam'), 'ellenmariebakke@gmail.com');
INSERT INTO RESTAURANT_ORDER VALUES
  (1004, to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), to_date('1927/11/10:05:30:40AM', 'yyyy/mm/dd:hh:mi:ssam'), 'sebasiten.feren@marquette.edu');
INSERT INTO RESTAURANT_ORDER VALUES
  (1005, to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), to_date('1927/11/10:05:30:40AM', 'yyyy/mm/dd:hh:mi:ssam'), 'hasan.barakat@marquette.edu');
INSERT INTO RESTAURANT_ORDER VALUES
  (1006, to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), to_date('1927/11/10:05:30:40AM', 'yyyy/mm/dd:hh:mi:ssam'), 'praveen.madiraju@marquette.edu');
INSERT INTO RESTAURANT_ORDER VALUES
  (1007, to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), to_date('1927/11/10:05:30:40AM', 'yyyy/mm/dd:hh:mi:ssam'), 'liamfruzyna@gmail.com');
INSERT INTO RESTAURANT_ORDER VALUES
  (1008, to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), to_date('1927/11/10:05:30:40AM', 'yyyy/mm/dd:hh:mi:ssam'), 'liamfruzyna@gmail.com');
INSERT INTO RESTAURANT_ORDER VALUES
  (1009, to_date('1927/11/10:05:30:10AM', 'yyyy/mm/dd:hh:mi:ssam'), to_date('1927/11/10:05:30:40AM', 'yyyy/mm/dd:hh:mi:ssam'), 'liamfruzyna@gmail.com');