    app.controller('ShareController',function($scope,$http ){
    	$http.get("api/allshares")
        .then(function(response) {
            $scope.shares = response.data;
        });
    	$scope.sselected=[];
    	$scope.sselected=[];
		$scope.ssearch="";
		$scope.sraise="";
		$scope.sprice="";
    	$scope.addSelected = function(){
    	$scope.search=$scope.ssearch;
		$scope.raise=$scope.sraise;
		$scope.price=$scope.sprice;
    	}
    });
    app.controller('RaisingController',function($scope,$http ){
    	$http.get("api/dailyraisers")
        .then(function(response) {
            $scope.shares = response.data;
        });
    });
    app.controller('LoosingController',function($scope,$http ){
    	var req = {
    			 method: 'POST',
    			 url: 'api/search',
    			 headers: {
    				 'Content-Type': 'application/json'
    			 },
    			 data: { sname: "TCS" ,startDate:"2016-10-15" }
    			}
    	$http(req)
        .then(function(response) {
            $scope.shares = response.data;
        });
    });
    app.controller('SearchController',function($scope,$http ){
    	
    	$scope.searchSelected = function(){
    		var req = {
       			 method: 'POST',
       			 url: 'api/search',
       			 headers: {
       				 'Content-Type': 'application/json'
       			 },
       			 data: { sname: $scope.srName ,startDate:$scope.startDate,endDate:$scope.endDate}
       			}
       	$http(req)
           .then(function(response) {
               $scope.shares = response.data;
           });
    		
        	}
    	
    	
    });
    app.controller('GrowthController',function($scope,$http ){
    	$http.get("api/growth")
        .then(function(response) {
            $scope.shares = response.data;
        });
    });
	
    app.config(function($stateProvider) {
    	
    	  $stateProvider.state('shares', {
    		    url: 'shares',
    		    templateUrl: 'ShareSearch.html',
    		    controller: 'ShareController'
    		  }).state('raisingShares', {
    		    url: 'raisingShares',
    		    templateUrl: 'ShareSearch.html',
    		    controller: 'RaisingController'
    		  }
    	  ).state('loosingShares', {
  		    url: 'loosingShares',
		    templateUrl: 'ShareSearch.html',
		    controller: 'LoosingController'
		  }
    	 ).state('growthWise', {
		    url: 'growth',
		    templateUrl: 'sharegrowth.html',
		    controller: 'GrowthController'
			}
		 ).state('searchShare', {
			    url: 'searchShare',
			    templateUrl: 'ShareDBSearch.html',
			    controller: 'SearchController'
				}
			 );
    	});
    app.filter('pricefilter', function() {
	return function (items,searchPrice){
		
		 if(!searchPrice){
	            return items;
	        }
		if( ! items )
			return items;
		
		 var filtered = [];
		 //alert(searchPrice)
		 for (var i = 0; i < items.length; i++) {
			 if(items[i].prices[0]){
			 if(parseFloat(items[i].prices[0].price)  >= parseFloat(searchPrice) ){
				// console.log(items[i].prices[0].price)
				 filtered.push(items[i]);
			 }
		 }
		 }
		 //alert(filtered.leng)
		 return filtered;
	};
	
});
    
    app.filter('namefilter', function() {
    	return function (items,search){
    		 if(!search){
    	            return items;
    	        }
    		if( ! items )
    			return items;
    		
    		 var filtered = [];
    		
    		 for (var i = 0; i < items.length; i++) {
    			 if(items[i].name.indexOf(search)>=0 ){
    				 filtered.push(items[i]);
    			 }
    		 }
    		 //alert(filtered.leng)
    		 return filtered;
    	};
    	
    });
    
    
    app.filter('raisingfilter', function() {
    	return function (items,raise){
    		
    		 if(!raise){
    	            return items;
    	        }
    		if( ! items )
    			return items;
    		
    		 var filtered = [];
    		 var shid;
    		 var matchCnt = raise < 0 ? -raise :raise;
    		 var cnt;
    		 for (var i = 0; i < items.length; i++) {
    			 cnt = 0;
    			 for (var j = 0; j < items[i].prices.length; j++) {
    				
    				 if(cnt >= matchCnt){
	    				 filtered.push(items[i]);
	    				 //console.log("Found"+items[i].name+" Cnt "+cnt+"  matchCnt777 "+matchCnt);
	    				 break;
	    			 }
    				 if(raise>0) {
	    				 if(parseFloat(items[i].prices[j].price)  >= parseFloat(items[i].prices[j].prevPrice) ){
		    				cnt++;
		    			 }else{
		    				// console.log("Breaking "+items[i].name+" at "+cnt);
		    				 break;
		    			 }
    				 }else  {
	    				 if(parseFloat(items[i].prices[j].price)  <= parseFloat(items[i].prices[j].prevPrice) ){
			    				cnt++;
			    			 }else{
			    				// console.log("Breaking "+items[i].name+" at "+cnt);
			    				 break;
			    			 }
	    				 }
	    			
    		 }
    			
    		 }
    		 return filtered;
    	};
    	
    });
       