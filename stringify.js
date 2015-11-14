var stringify = (function(){
var base, list, hash, toString, checkValueType;

//데이터 형식을 체크하여 문자열의 경우 쌍따옴표로 감쌈(")
checkValueType = function(val){
	switch(typeof val){
		case "number":
		case "boolean":
		case "object":
			return val;
		case "undefined":
			return undefined;
		default :
		return '"' + val + '"';
	}
};
//데이터 형식이 Array 나 JSON 형식이 아닐 경우
base = function() {
	return addCover(checkValueType(this));
};
//데이터 형식이 Array 일 경우 
list = function() {
	var i = this.length;
	while(i--){
		this[i] = checkValueType(toString(this[i], i));
	} 
	return  "[" + Array.prototype.join.call(this,',') +"]";
};
//데이터 형식이 JSON 형식일 경우
hash = function() {
	var	result = [], k;
	for(k in this){
        if(this.hasOwnProperty(k)) {
           if(typeof this[k] != 'function'){ //toString function이 추가 되어 있어 처리
           	result.push((checkValueType(k)+':'+checkValueType(toString(this[k], k))));
           } 
		}
    }
	return "{" + result.join (',') + "}";
};
//toString을 override 하여 처리 idx가 undefined 일 경우 최초 호출을 나타냄
toString = function(val, idx){
    var toStr;
	if(val && typeof val == 'object'){
		toStr = 'length' in val ? list : hash;
	}else toStr = base;
    val.toString = function(){
        return toStr.call(this);
    };
    //일반 형식의 문자가 넘어올 경우, JSON형식으로 표출 - 이 경우는 val.toString  이 호출 되지 않음
    if(typeof idx == "undefined" && typeof val != "object"){
    	return checkValueType(val);
    }
	return val;
};
return toString;
})(); 

//toString 이 호출 되거나, +'' 를 해줘야 합니다.
console.log(stringify([1,'false', [1,"2", false, "false"],false])+''); //결과 : [1,"false",[1,"2",false,"false"],false]
console.log(stringify({a:123123,"C":{2:"ccc",4:"dfdf"}, "B":55, "CD":[2,"abc",4]})+''); //결과 :  {"a":123123,"C":{"2":"ccc","4":"dfdf"},"B":55,"CD":[2,3,4]}
console.log(stringify("test") + ''); //결과 : "test"
console.log(stringify(1234) + ''); //결과 : 1234
