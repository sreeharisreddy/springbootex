   var app = angular.module("chitApp",['ngResource','ui.router',"checklist-model"]);
    app.controller('ChitListController',function($scope,Chit,$state,$window,popupService ){
    	   	$scope.chits=Chit.query();

    	    $scope.deleteChit=function(chit){
    	        if(popupService.showPopup('Really delete this?')){
    	            chit.$delete(function(){
    	                $window.location.href='';
    	            });
    	        }
    	    }
    });
    app.controller('ChitViewController',function($scope,Chit,$stateParams){
    	//alert("ID:"+$stateParams.id);
    	$scope.chit=Chit.get({id:$stateParams.id});
    });
    app.controller('ChitEditController',function($scope,Chit,$state,$stateParams){
    	$scope.format = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    	$scope.chit=Chit.get({id:$stateParams.id});;
    	$scope.updateChit = function() { //Update the edited movie. Issues a PUT to /api/movies/:id
    	    $scope.chit.$update(function() {
    	      $state.go('chits');
    	    });
    	};
    });
    app.config(function($stateProvider) {
    	
    	  $stateProvider.state('chits', { // state for showing all movies
    		    url: 'chits',
    		    templateUrl: 'chits.html',
    		    controller: 'ChitListController'
    		  }).state('viewChit', { //state for showing single movie
    		    url: '/chits/:id/view',
    		    templateUrl: 'chit_view.html',
    		    controller: 'ChitViewController'
    		  }).state('newChit', { //state for adding a new movie
    		    url: '/chits/new',
    		    templateUrl: 'chit_create.html',
    		    controller: 'ChitCreateController'
    		  }).state('editChit', { //state for updating a movie
    		    url: '/chits/:id/edit',
    		    templateUrl: 'chit_edit.html',
    		    controller: 'ChitEditController'
    		  });
    	}).run(function($state) {
    		  $state.go('chits'); //make a transition to movies state when app starts
    		});
    
    
	app.controller('ChitCreateController',['$scope','Chit','$state', '$stateParams',function($scope,Chit,$state, $stateParams){
		$scope.chit = new Chit();
		$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    	$scope.addChit = function(){
    		console.log("You called to save");	
    		$scope.chit.$save(function(){
    			console.log("Saved Successfully");
    			$state.go("chits");
    		});
    	}
    }]);
    
    app.factory('Chit',function($resource){
    	return $resource('http://localhost:8080/springbootex/api/chits/:id',{ id: '@id'}, {
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