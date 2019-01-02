var express = require('express');
var router = express.Router();
var path = require('path');

module.exports = router;

router.get('/', function(req, res, next)
{
  res.sendFile(path.join(__dirname + '/../views/index.html'))
});

router.get('/about', function(req, res, next)
{
    res.sendFile(path.join(__dirname + '/../views/about.html'));
});