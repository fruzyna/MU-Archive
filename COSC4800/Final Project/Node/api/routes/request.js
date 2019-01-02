var express = require('express');
var oracledb = require('oracledb');
var router = express.Router();
var path = require('path');

module.exports = router;

oracledb.autoCommit = true;

var signin = {
  user          : "redacted",
  password      : "redacted",
  connectString : "redacted"
};

var invalidQuery = 'Invalid query, please check it and try again.'
var badConnection = 'Failed to connect to server, please check credentials.'

router.get('/restaurant', function(req, res, next)
{
  var q = req.query;
  query("SELECT r.rname, r.phone, r.email, r.hours, r.diningtype, r.streetno, r.streetname, r.city, r.state, r.zip, r.pricerange, r.deliveryflag, r.outdoorseatingflag, round(rev.av, 1), round(rev.co, 1) \
          FROM restaurant r, (SELECT avg(cr.rating) as av, count(cr.rating) as co FROM cust_review cr WHERE cr.restid = " + q.restid + ") rev \
          WHERE r.restid = " + q.restid, res);
});

router.get('/restcuisine', function(req, res, next)
{
  var q = req.query;
  query("SELECT rc.cname \
          FROM rest_cuisine rc \
          WHERE rc.restid = " + q.restid, res);
});

router.get('/restreview', function(req, res, next)
{
  var q = req.query;
  query("SELECT * \
          FROM cust_review cr \
          WHERE cr.restid = " + q.restid, res);
});

router.get('/resttopmenu', function(req, res, next)
{
  var q = req.query;
  query("SELECT m.fname, m.price \
          FROM ( \
            SELECT u.price AS price, o.foodname AS fname, count(o.foodname) AS num \
            FROM menu u, restaurant_odetails o \
            WHERE o.restid = " + q.restid + " AND \
              u.foodname = o.foodname AND \
              u.restid = o.restid \
            GROUP BY u.price, o.foodname) m \
          WHERE ROWNUM <= 3 \
          ORDER BY m.num desc", res);
});

router.get('/restmenu', function(req, res, next)
{
  var q = req.query;
  query('SELECT m.foodname, m.price \
          FROM menu m \
          WHERE m.restid = ' + q.restid, res);
});

router.get('/listrests', function(req, res, next)
{
  query('SELECT r.restid, r.rname, r.hours, r.pricerange, r.diningtype, (SELECT avg(cr.rating) FROM cust_review cr WHERE r.restid = cr.restid) \
          FROM restaurant r \
          GROUP BY r.restid, r.rname, r.hours, r.pricerange, r.diningtype', res);
});

router.get('/addreview', function(req, res, next)
{
  var q = req.query;
  query('INSERT INTO cust_review VALUES(Customer_ReviewId_Seq.nextval,\'' + q.email + '\',' + q.restid + ',\'' + q.title + '\',\'' + q.body + '\',sysdate,' + q.rating + ')', res);
});

router.get('/login', function(req, res, next)
{
  var q = req.query;
  query('SELECT fname, minit, lname, email \
         FROM restaurant_customer \
         WHERE email = \'' + q.email + '\' \
          AND password = \'' + q.password + '\'', res);
});

router.get('/getaddress', function(req, res, next)
{
  var q = req.query;
  query('SELECT streetno, streetname, city, state, zip \
         FROM restaurant_customer \
         WHERE email = \'' + q.email + '\'', res);
});

//example: http://192.168.1.169:4800/api/create/?email=liamfruzyna@gmail.com&fname=Liam&minit=M&lname=Fruzyna&phone=6306776675&password=pass&streetno=1500&streetname=W%20Wells%20St&city=Milwaukee&state=WI&zip=53233
router.get('/create', function(req, res, next)
{
  var q = req.query;
  var qry = 'INSERT INTO restaurant_customer VALUES(\'' + q.email + '\',\'' + q.fname + '\',\'' + q.minit + '\',\'' + q.lname + '\',\'' + q.phone + '\',\'' + q.password + '\',' + q.streetno + ',\'' + q.streetname + '\',\'' + q.city + '\',\'' + q.state + '\',' + q.zip + ')';
  query(qry, res);
});

router.get('/foodcost', function(req, res, next)
{
  var q = req.query;
  query('SELECT m.price \
          FROM menu m \
          WHERE m.foodname = ' + q.ono + ' \
                m.restid = ' + q.restid, res);
});

router.get('/orderstatus', function(req, res, next)
{
  var q = req.query;
  query('SELECT o.orderdate, o.orderdeliveryflag, o.orderpickupflag, o.orderdineinflag, o.orderdeliverydate, o.orderpickupdate, o.orderdineinreceiveddate \
          FROM restaurant_order o \
          WHERE o.no = ' + q.ono, res);
});

router.get('/orderitems', function(req, res, next)
{
  var q = req.query;
  query('SELECT o.foodname, o.restid, o.qty, m.price, r.rname \
          FROM restaurant_odetails o, menu m, restaurant r \
          WHERE o.ono = ' + q.ono + ' AND \
              o.foodname = m.foodname AND \
              o.restid = m.restid AND \
              r.restid = o.restid', res);
});

router.get('/lastono', function(req, res, next)
{
  var q = req.query;
  query('SELECT max(ono) FROM restaurant_order', res);
});

router.get('/neworder', function(req, res, next)
{
  var q = req.query;
  query('insert into RESTAURANT_ORDER VALUES (Restaurant_Order_Ono.nextval,sysdate, \'' + q.email + '\',\'' + q.dinein + '\',\'' + q.delivery + '\',\'' + q.carryout + '\',null,null,null)', res)
});

router.get('/newitem', function(req, res, next)
{
  var q = req.query;
  query('insert into RESTAURANT_ODETAILS VALUES(\'' + q.ono + '\',\'' + q.food + '\',\'' + q.restid + '\',\'' + q.qty + '\')', res)
});

router.get('/userorders', function(req, res, next)
{
  var q = req.query;
  query('SELECT o.ono, o.orderdate, o.orderdeliveryflag, o.orderpickupflag, o.orderdineinflag, o.orderdeliverydate, o.orderpickupdate, o.orderdineinreceiveddate \
          FROM restaurant_order o \
          WHERE o.custemail like \'' + q.email + '\'', res);
});

router.get('/userreviews', function(req, res, next)
{
  var q = req.query;
  query('SELECT cr.reviewid, cr.reviewtitle, cr.rating, r.rname, cr.reviewdate, cr.reviewdescr \
          FROM cust_review cr, restaurant r \
          WHERE cr.restid = r.restid AND \
            cr.custemail LIKE \'' + q.email + '\'', res);
});

router.get('/search', function(req, res, next)
{
  var q = req.query;
  query('select distinct r.restId, r.rname, r.diningtype, r.pricerange \
          from restaurant r, rest_cuisine c, menu m \
          where r.rname like \'' + q.search + '%\' OR \
           r.DiningType like \'' + q.search + '%\' OR \
           (c.CName like \'' + q.search + '%\' AND \
           c.restId=r.restId) OR \
           (m.FoodName like \'' + q.search + '%\' AND \
           m.restId=r.restId)', res);
});

function query(query, res)
{
  console.log('Query: ' + query);
  oracledb.getConnection(
    signin,
    function(err, connection)
    {
      if (err)
      {
        console.error(err);
        res.send(badConnection);
        return;
      }
      connection.execute(
        query,
        function(err, result)
        {
          if (err) { res.send(invalidQuery + "(" + err + ")"); console.error(err); return; }
          var output = result.rows;
          if(output == null)
          {
            output = "Success";
          }
          console.log('Result: ' + output);
          res.send(output);
          connection.close(function (err)
          {
            if(err)
            {
              console.error(err.message);
            }
          });
        });
    });
}
