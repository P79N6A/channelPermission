var reg = /\$\{(.*?)\}/g;
var regStatic = /@\{(.*?)\}/g;
function formula2nodes(H, dlgTreeNodeMap) {
  var __nodes = [];
  var elementArray = formula2keys(H);//得到了解析之后的公式元素
  for (var index = 0; index < elementArray.length; index++) {
    var element = elementArray[index];
    if (!element) {
      continue;
    }
    if (element.type === 'static') {
      __nodes.push(element);
    }
    if (element.type === 'node') {
      if(isVarEmpty(dlgTreeNodeMap[element.key])){
        __nodes.push({text:'error'})
      }else{
        dlgTreeNodeMap[element.key].type = 'node';
        __nodes.push(dlgTreeNodeMap[element.key]);
      }
    }
    if (element.type === 'operator') {
      __nodes.push(element);
    }
  }

  return __nodes
}
function formula2keys(str) {
  if(!str){
    return [];
  }
  var regRess = [];
  var res;
  while ((res = reg.exec(str)) != null) {
    var regRes = {};
    regRes.reg = res;
    regRes.type = "node";
    regRes.start = res.index;
    regRes.formula = res[0];
    regRes.key = res[1];
    regRes.end = res.index + res[0].length - 1;
    regRess.push(regRes);
  }
  while ((res = regStatic.exec(str)) != null) {
    var regRes = {};
    regRes.reg = res;
    regRes.type = 'static';
    regRes.start = res.index;
    regRes.formula = res[0];
    regRes.key = res[1];
    regRes.end = res.index + res[0].length - 1;
    regRess.push(regRes);
  }
  regRess.sort(function (a, b) {
    return a.start - b.start;
  });
  var allStrArray = [];
  var strLength = str.length;
  var i = 0;
  for (var index = 0; index < regRess.length; index++) {
    var tempRes = regRess[index];
    var tempStr = str.substring(i, tempRes.start);
    extractOperator(tempStr, allStrArray);
    allStrArray.push(
        {type: tempRes.type, key: tempRes.key, formula: tempRes.formula});
    i = tempRes.end + 1;
  }
  tempStr = str.substring(i);
  extractOperator(tempStr, allStrArray);
  return allStrArray;
}

function extractOperator(str, allStrArray) {

  var letters = extractLetter(str);
  for (var index = 0; index < letters.length; index++) {
    var letter = letters[index];
    if (letterObj[letter]) {
      allStrArray.push(letterObj[letter]);
    }
  }
}

function extractLetter(str) {
  var opts = [];
  var str = str.trim();
  for (var index = 0; index < str.length; index++) {
    var operator = str[index].trim();
    opts.push(operator);
  }
  return opts;
}

var letterObj = {
  '+': {type: 'operator', act: '+', act0: '+'},
  '-': {type: 'operator', act: '-', act0: '-'},
  '*': {type: 'operator', act: '*', act0: '×'},
  '/': {type: 'operator', act: '/', act0: '÷'},
  '(': {type: 'operator', act: '(', act0: '('},
  ')': {type: 'operator', act: ')', act0: ')'}
};

function findKeyWord(formula, envH, env) {
  var keywords = null;
  var res;
  while ((res = reg.exec(formula)) != null) {
    var key = res[1];
    keywords = keywords || [];
    keywords.push(key);
  }
  return keywords;
}