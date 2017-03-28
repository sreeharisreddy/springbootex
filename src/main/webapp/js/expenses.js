    app.controller('ExpenseController',function($scope,Expense,$state,$window,popupService ){
    	   	$scope.expenses=Expense.query();

    	    $scope.deleteExpense=function(expense){
    	        if(popupService.showPopup('Really delete this?')){
    	            expense.$delete(function(){
    	                $window.location.href='';
    	            });
    	        }
    	    }

            $scope.getTotal = function(currency){
             if (angular.isUndefined($scope.expenses )) {
                     return 0;
            }
            var total = 0;
            
            angular.forEach($scope.expenses, function(value, key){
                if(value.CURRENCY == currency){
                  total = (parseFloat(expense.AMOUNT));
                  }
              });
            return total;
            }
    });
    app.controller('ExpenseViewController',function($scope,Expense,$stateParams){
    	//alert("ID:"+$stateParams.id);
    	$scope.expense=Expense.get({id:$stateParams.id});
    });
    app.controller('ExpenseEditController',function($scope,Expense,$state,$stateParams){
    	$scope.format = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    	$scope.expense=Expense.get({id:$stateParams.id});;
    	$scope.updateExpense = function() { //Update the edited movie. Issues a PUT to /api/movies/:id
    	    $scope.expense.$update(function() {
    	      $state.go('expenses');
    	    });
    	};
    });
    app.config(function($stateProvider) {
    	
    	  $stateProvider.state('expenses', { // state for showing all movies
    		    url: 'expenses',
    		    templateUrl: 'expenselist.html',
    		    controller: 'ExpenseController'
    		  }).state('viewExpense', { //state for showing single movie
    		    url: '/expenses/:id/view',
    		    templateUrl: 'expense_view.html',
    		    controller: 'ExpenseViewController'
    		  }).state('newExpense', { //state for adding a new movie
    		    url: '/expenses/new',
    		    templateUrl: 'expense_create.html',
    		    controller: 'ExpenseCreateController'
    		  }).state('editExpense', { //state for updating a movie
    		    url: '/expenses/:id/edit',
    		    templateUrl: 'expense_edit.html',
    		    controller: 'ExpenseEditController'
    		  });
    	}).run(function($rootScope, $templateCache) {
                $rootScope.$on('$viewContentLoaded', function() {
             $templateCache.removeAll();
        });
})
    
    
	app.controller('ExpenseCreateController',['$scope','$state', '$stateParams','$http', function($scope,$state, $stateParams,$http){
		  	$scope.addExpense = function(expense){
            alert(expense.method);
               return $http.post('expensedao.php', expense).then(function (results) {
                 return results;
            });
    		}
    }]);
    
   	