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

koreasoft.namespace('koreasoft.view.common');

koreasoft.view.common = function() { 
	var _init = function(){
		$("#logoutBtn").on("click", function(){
			$("#logoutForm").submit();
		});
		
		var _backBtn = document.getElementById("backBtn");
		if(_backBtn !=undefined){
			$(_backBtn).on("click", function(){
				window.history.back();
			})
		}
				
	}
	
	_init();
};

$(function(){
	new koreasoft.view.common();
});