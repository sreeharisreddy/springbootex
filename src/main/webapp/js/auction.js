    app.controller('AuctionListController',function($scope,Auction,$state,$window,popupService ){
    	   	$scope.auctions=Auction.query();

    	    $scope.deleteAuction=function(auction){
    	        if(popupService.showPopup('Really delete this?')){
    	            auction.$delete(function(){
    	                $window.location.href='';
    	            });
    	        }
    	    }
    });
    app.controller('AuctionViewController',function($scope,Auction,$stateParams){
    	//alert("ID:"+$stateParams.id);
    	$scope.auction=Auction.get({id:$stateParams.id});
    });
    app.controller('AuctionEditController',function($scope,Auction,$state,$stateParams){
    	$scope.format = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    	$scope.auction=Auction.get({id:$stateParams.id});;
    	$scope.updateAuction = function() { //Update the edited movie. Issues a PUT to /api/movies/:id
    	    $scope.auction.$update(function() {
    	      $state.go('auctions');
    	    });
    	};
    });
    app.config(function($stateProvider) {
    	
    	  $stateProvider.state('auctions', { // state for showing all movies
    		    url: 'auctions',
    		    templateUrl: 'auctions.html',
    		    controller: 'AuctionListController'
    		  }).state('viewAuction', { //state for showing single movie
    		    url: '/auctions/:id/view',
    		    templateUrl: 'auction_view.html',
    		    controller: 'AuctionViewController'
    		  }).state('newAuction', { //state for adding a new movie
    		    url: '/auctions/new',
    		    templateUrl: 'auction_create.html',
    		    controller: 'AuctionCreateController'
    		  }).state('editAuction', { //state for updating a movie
    		    url: '/auctions/:id/edit',
    		    templateUrl: 'auction_edit.html',
    		    controller: 'AuctionEditController'
    		  });
    	});
    
    
	app.controller('AuctionCreateController',['$scope','Auction','$state', '$stateParams',function($scope,Auction,$state, $stateParams){
		$scope.auction = new Auction();
		$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    	$scope.addAuction = function(){
    		console.log("You called to save");	
    		$scope.auction.$save(function(){
    			console.log("Saved Successfully");
    			$state.go("auctions");
    		});
    	}
    }])
    .controller('AuctionCreateController',['$scope','Auction','$state', '$stateParams',function($scope,Auction,$state, $stateParams){
		
    	$scope.addAuction = function(){
    		console.log("You called to Auction To Save");	
    		$scope.auction.$save(function(){
    			console.log("Saved Successfully");
    			$state.go("auctions");
    		});
    	}
    	
	}]);
    
    app.factory('Auction',function($resource){
    	return $resource('http://localhost:8080/springbootex/api/auctions/:id',{ id: '@id'}, {
    		    update: {
    		        method: 'PUT'
    		      }    	
    		    });
    })
    .factory('Auction',function($resource){
    	return $resource('http://localhost:8080/springbootex/api/auctions/:id',{ id: '@id'}, {
    		    update: {
    		        method: 'PUT'
    		      }    	
    		    });
    });
   app.service('popupService',['$window',function($window){
    	this.showPopup=function(message){
    	return $window.confirm(message); //Ask the users if they really want to delete
    	}
    	}]);
   
   app.directive('jqdatepicker', function ($filter) {
	    return {
	        restrict: 'A',
	        require: 'ngModel',
	         link: function (scope, element, attrs, ngModelCtrl) {
	            element.datepicker({
	                dateFormat: 'dd/mm/yy',
	                onSelect: function (date) {   
	                    var ar=date.split("/");
	                    date=new Date(ar[2]+"-"+ar[1]+"-"+ar[0]);
	                    ngModelCtrl.$setViewValue(date.getTime());            
	                    scope.$apply();
	                }
	            });
	            ngModelCtrl.$formatters.unshift(function(v) {
	            return $filter('date')(v,'dd/MM/yyyy'); 
	            });

	        }
	    };
	});