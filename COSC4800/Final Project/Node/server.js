var express = require('express'),
app = express(),
port = process.env.PORT || 4800;

var request = require('./api/routes/request');
var index = require('./api/routes/index');

app.listen(port);

console.log('Server running on port: ' + port);

app.use('/', index)
app.use('/api', request)
app.use(function(req, res, next) {
    res.send('Error 404: Page not found');
  });

//run with: nodemon --exec npm run start