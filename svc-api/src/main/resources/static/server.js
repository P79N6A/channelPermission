var express = require("express");
var app = express();
var port=3000;
app.use("/",express.static(__dirname + "/"));
app.get('/hello',function(req,res){
  res.send('hello world');
});
app.listen(port,function(){
  console.log('开始监听端口'+port);
});