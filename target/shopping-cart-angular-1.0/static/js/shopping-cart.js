var shoppingCartApp = angular.module('shoppingCartApp', ['ngRoute']);

shoppingCartApp.config(['$routeProvider', function($routeProvider){
	$routeProvider
		.when('/', {
			templateUrl: '/shopping-cart-angular/static/pages/content/displayCategories.html',
			controller: 'angular.shoppingcart.category.controller',
			resolve: {
				'categoryData': ['getGlobalCategoryListService', function(getCategoryList){
					return getCategoryList;
				}]
			}
		})
		.when('/login', {
			templateUrl: '/shopping-cart-angular/static/pages/content/login.html',
			controller: 'angular.shoppingcart.login.controller',
		})		
		.when('/viewProductDetails', {
			templateUrl: '/shopping-cart-angular/static/pages/content/viewProductDetails.html',
			controller: 'angular.shoppingcart.selected.product.controller'
		})
		.otherwise({
			redirectTo: '/'
		});
}])

shoppingCartApp.factory('customerInfoService', function(){
	var username = '';
	var firstname = '';
	var lastname = '';
	var customerInfo = {
		getUsername:function(){
			return username;
		},
		setUsername:function(data){
			username=data;
		},
		getFirstname:function(){
			return firstname;
		},
		setFirstname:function(data){
			firstname=data;
		},
		getLastname:function(){
			return lastname;
		},
		setLastname:function(data){
			lastname=data;
		}
	}
	return customerInfo;
});

/*shoppingCartApp.service('sessionValidityCheckService', ['$http', '$q', '$location', 'customerInfoService', function($http, $q, $location, custInfoSvc){
	var loginInfo = $q.defer();
	// Call display categories to retrieve list of categories from backend controller
	var config = {withCredentials:true};
	$http.get('/shopping-cart-angular/login.htm', config)
		.then(
			function(response) {
				alert(JSON.stringify(response));

				// Update global var customerInfoService
				custInfoSvc.setFirstname(response.data.firstName);
				custInfoSvc.setLastname(response.data.lastName);
				custInfoSvc.setUsername(response.data.username);
				
				$location.path('/displayCategories');
				
				loginInfo.resolve(response);
			}, 
			function(response) {
				alert(JSON.stringify(response));
				return loginInfo.reject(response);
			}
		);
	return loginInfo.promise;
}]);*/

shoppingCartApp.service('getGlobalCategoryListService', ['$http', '$q', '$location', 'catlistService', function($http, $q, $location, catlistService){
	var globalCategoryList = $q.defer();
	// Call display categories to retrieve list of categories from backend controller
	$http.get('/shopping-cart-angular/displayCategories.htm')
		.then(
			function(response) {
				alert(JSON.stringify(response));

				// Update global var categoryList
				$.each(response.data.categories, function(key, value){
					catlistService.push(value);
				});
				globalCategoryList.resolve(response);
			}, 
			function(response) {
				alert(JSON.stringify(response));
				$location.path('/');
				return globalCategoryList;
			}
		);
	return globalCategoryList.promise;
}]);

shoppingCartApp.service('catlistService', function(){
	var catlist = [];
	return catlist;
});

shoppingCartApp.factory('itemDataFactory', function(){
	//var productData = {productData:''};
	return {productData:{}};
});

shoppingCartApp.factory('selectedProduct', function(){
	var product = {};
	return product;
});

shoppingCartApp.service('cartService', function(){
	//var productData = {productData:''};
	return cartCount;
});

shoppingCartApp.controller('angular.shoppingcart.navbar.controller', ['$scope', 'customerInfoService', function($scope, custInfoSvc){
	$scope.firstname = custInfoSvc.firstName;
	// Update customer info in the nav-bar
	$scope.$on('customerInfoUpdateEvent', function(){
		$scope.firstname = custInfoSvc.getFirstname();
	});
}])

shoppingCartApp.controller('angular.shoppingcart.sidebar.controller', ['$scope','$location', function($scope, $location){
	$scope.login = function(){
		alert("Moving to login");
		$location.path("/login");
	}
	
	$scope.signup = function(){
		alert("SignUp up not implemented");
	}
	
	$scope.viewOrders = function(){
		alert("View Orders not implemented");
	}

	$scope.logout = function(){
		alert("Logout not implemented");
	}
}])

shoppingCartApp.controller('angular.shoppingcart.login.controller', ['$scope','customerInfoService','$location','$rootScope', '$http', function($scope, custInfoSvc, $location, $rootScope, $http){
	$scope.message = "";
	$scope.username = "";
	$scope.password = "";
	$scope.validateLogin = function(){
		var customer = {'username':$scope.username, 'password':$scope.password}
		var config = {headers:{'Content-Type':'application/json;charset=UTF-8', 'Accept':'application/json;charset=UTF-8', 'Cache-Control': 'no-cache'},
					  withCredentials:true};
		$http.post('/shopping-cart-angular/validateLogin.htm', customer, config)
			.then(
				function(response){
					alert(JSON.stringify(response));
					// Update global var Customer
					custInfoSvc.setFirstname(response.data.firstName);
					custInfoSvc.setLastname(response.data.lastName);
					custInfoSvc.setUsername(response.data.username);
					
					// Broad cast the updated info so that nav-bar gets updated
					$rootScope.$broadcast('customerInfoUpdateEvent');
					
					alert($location.path());
					$location.path("/");
					alert($location.path());
					//$rootScope.$digest();
				},				  
				function(response){
					alert(JSON.stringify(response));
					$scope.message = "Invalid username or password!";  
				}
			);
	}
}])

shoppingCartApp.controller('angular.shoppingcart.category.controller', ['$scope', '$rootScope', '$http', 'catlistService', 'itemDataFactory', function($scope, $rootScope, $http, catlistService, itemDataFactory){
	//$scope.productMap = {};
	//alert(JSON.stringify(globalCategoryList)+"------ length"+globalCategoryList.length);
	$scope.categoryList = catlistService;
	$scope.showProducts = function(){
		alert($scope.categoryId);
		var request = {
			method:"GET",
			url:"/shopping-cart-angular/retrieveProducts.htm?categoryId="+$scope.categoryId,
			headers:{'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8', 'Accept':'application/json;charset=UTF-8', 'Cache-Control': 'no-cache'}
		};		
		$http(request)
			.then(function(response){
				alert(JSON.stringify(response))

				// Update global var
				itemDataFactory.productData = response.data.productMap;

				// Iterate entire watch list for this scope to check if any of object has been updated
				//$rootScope.$digest();
				//alert(JSON.stringify(itemDataFactory.productData));
			},
			function(response){
				alert("Error : "+ status + " : " + xhr.status);
			});
		
		/*
		$.ajax({
			type:"GET",
			url:"/shopping-cart-angular/retrieveProducts.htm?categoryId="+$scope.categoryId,
			cache:false,
			contentType:"application/x-www-form-urlencoded;charset=UTF-8",
			dataType:"json",
			success: function(data, status, xhr) {
				//alert("Status : "+ status + "----------"+ JSON.stringify(data))

				// Update global var
				itemDataFactory.productData = data.productMap;

				// Iterate entire watch list for this scope to check if any of object has been updated
				$rootScope.$digest();
				//alert(JSON.stringify(itemDataFactory.productData));
			},
			error: function(xhr, status) {
				alert("Error : "+ status + " : " + xhr.status);
			}
		});*/
	};
	/*
	$scope.$watchCollection(
		function(){ return categoryList; },
		function(newVal, oldVal){
			//Reset productDataMap
			//$scope.categoryList = [];

			// Then push new products
			$.each(newVal, function(key, productObj){
				$scope.categoryList.push(productObj);
			});
			//alert(JSON.stringify($scope.categoryList));
		});*/
}]);

shoppingCartApp.controller('angular.shoppingcart.item.list.controller', ['$scope', '$rootScope', 'itemDataFactory', 'selectedProduct', '$location', function($scope, $rootScope, itemDataFactory, selectedProduct, $location){
	$scope.productDataList = [];
	$scope.chunkedProductDataList = [];
	$scope.showProducts = false;
	$scope.viewProductDetails=function(productId){
		selectedProduct.product = itemDataFactory.productData[productId];
		alert(JSON.stringify(selectedProduct.product));
		$location.path("/viewProductDetails");
		alert($location.path());
		//$rootScope.$digest();
	};

	$scope.$watchCollection(
		function(){ return itemDataFactory.productData; },
		function(newVal, oldVal){
			//Reset productDataMap
			$scope.productDataList = [];
			$scope.chunkedProductDataList = [];

			// Then push new products
			$.each(newVal, function(key, productObj){
				$scope.productDataList.push(productObj);
			});

			// Chunk the products into 3 columns
			$scope.chunkedProductDataList = chunk($scope.productDataList, 3)

			// Check length to determine whether to show the product table or not
			if($scope.productDataList.length > 0){
				$scope.showProducts = true
			}
			else{
				$scope.showProducts = false;
			}
    		//alert(JSON.stringify($scope.productDataMap));
	});
}]);

shoppingCartApp.controller('angular.shoppingcart.selected.product.controller', ['$scope', '$rootScope', 'selectedProduct', function($scope, $rootScope,  selectedProduct){
	//$rootScope.$digest();
	$scope.productDetails = selectedProduct.product;
	$scope.productDetails.qtyOrdered=0;

	$scope.addToCart = function(){
		alert($scope.product.id+", "+$scope.product.name+", "+$scope.product.unitPrice+", "+$scope.product.inStockQty+", "+$scope.product.qtyOrdered);
		$.ajax({
			type:"POST",
			url:"/shopping-cart/addToCart.htm",
			cache:false,
			contentType:"application/json;charset=UTF-8",
			dataType:"json",
			data: JSON.stringify($scope.product),
			success: function(data, status, xhr) {
				alert("Status : "+ status+", Data : "+data);
			},
			error: function(xhr, status) {
				alert("Error : "+ status + " : " + xhr.status);
			}
		});
	};

	$scope.$watchCollection(
		function(){ return selectedProduct.product; },
		function(newVal, oldVal){
			//Set new value to productDetails
			$scope.productDetails = newVal;
		});
}]);


shoppingCartApp.controller('angular.shoppingcart.cart.controller', ['$scope', function($scope){

	$scope.productId={};
	$scope.productName={};
	$scope.productQtyOrdered={};

	$scope.updateCartItem = function(productId){
		alert(productId+", "+$scope.productId[productId]+", "+$scope.productName[productId]+", "+$scope.productQtyOrdered[productId]);
		var product = {"id":productId, "name":$scope.productName[productId], "qtyOrdered":$scope.productQtyOrdered[productId]}
		$.ajax({
			type:"POST",
			url:"/shopping-cart-angular/updateCart.htm",
			cache:false,
			contentType:"application/json;charset=UTF-8",
			dataType:"json",
			data: JSON.stringify(product),
			success: function(data, status, xhr) {
				alert("Status : "+ status+", Data : "+JSON.stringify(data));
			},
			error: function(xhr, status) {
				alert("Error : "+ status + " : " + xhr.status);
			}
		});
	}

	$scope.removeCartItem = function(productId){
		alert(productId+", "+$scope.productId[productId]+", "+$scope.productName[productId]+", "+$scope.productQtyOrdered[productId]);
		var product = {"id":productId, "qtyOrdered":0}
		$.ajax({
			type:"POST",
			url:"/shopping-cart/updateCart.htm",
			cache:false,
			contentType:"application/json;charset=UTF-8",
			dataType:"json",
			data: JSON.stringify(product),
			success: function(data, status, xhr) {
				alert("Status : "+ status+", Data : "+JSON.stringify(data));
				// Remove the cart item html and hide the div
				$("#cartItem_"+productId).html("");
				$("#cartItem_"+productId).hide();
			},
			error: function(xhr, status) {
				alert("Error : "+ status + " : " + xhr.status);
			}
		});
	}
}])

// Split the array into fixed number of columns
function chunk(arr, size) {
	var newArr = [];
	for (var i=0; i<arr.length; i+=size) {
		newArr.push(arr.slice(i, i+size));
	}
	return newArr;
}

// Ajax call to retrieve products
function updateCart(product){
	var productMap = {};


	return productMap;
}
