const router = require('./router')
const http = require('http')

http.createServer(function (req, res) {
    router.register(req, res, [
        {
            'url': '/hello',
            'handler': hello
        }
    ])
}).listen(9000);

const hello = function (req, res) {
    console.log('/hello')
    res.setHeader('Content-Type', 'text/html; charset=utf-8')
    // res.write('<head><meta charset="UTF-8"/></head>')
    // res.writeHeader(200, {'ContentType': 'text/html; charset=utf-8'})
    res.write('<h1>/hello ！</h1>')
    res.end()
}

console.log('服务器已启动：http://localhost:9000')
