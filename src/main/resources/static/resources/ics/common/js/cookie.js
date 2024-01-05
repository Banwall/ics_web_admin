var koreasoft = koreasoft || {}; 
koreasoft.namespace = function(ns_string) { 
	var parts = ns_string.split('.'), parent = koreasoft, i; 
	if (parts[0] === "koreasoft") { 
		parts = parts.slice(1); 
	} 
	for (i = 0; i < parts.length; i += 1) { 
		if (typeof parent[parts[i]] === "undefined") { 
			parent[parts[i]] = {}; 
		} 
		parent = parent[parts[i]]; 
	} 
	return parent; 
} 

koreasoft.namespace('koreasoft.modules.cookie');

koreasoft.modules.cookie = function() { 
	///////////	private 
	
	///////////	public 
	function setCookie(name, value, exp) {
		var date = new Date();
		date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
		document.cookie = name + '=' +  value + ';expires=' + date.toUTCString() + ';path=/';
	}
	
	function getCookie(name) {
		var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
		return value ? value[2] : null;
	}

	function deleteCookie(name) {
		setCookie(name, null , 0);
	}

	// 특권 메서드가 들어있는 객체를 반환 
	return {		
		   setCookie : setCookie
		 , getCookie : getCookie
		 , deleteCookie : deleteCookie 
	}; 
}(); 