const express = require('express')
const server = express()

server.get('/', (req, res) => res.send("<h1>Hello Express !</h1>"))
    .listen(9000, () => console.log('Start on port 9000...'))

