const express = require('express');
const path = require('path');
const app = express();
const port = process.env.PORT || 3000;

// Import your router(s)
const indexRouter = require('./app_server/routes/index');

// View engine setup (Handlebars)
app.set('views', path.join(__dirname, 'app_server', 'views'));
app.set('view engine', 'hbs');

// Serve static files from the 'public' folder
app.use(express.static(path.join(__dirname, 'public')));

// Register routes
app.use('/', indexRouter);

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});