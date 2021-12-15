angular.module('app', []).controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/app/api/v1';


    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });
    };

    $scope.loadOrders = function () {
        $http.get(contextPath + '/products/order')
            .then(function (responce) {
                $scope.OrderList = responce.data;
                console.log(responce)
            });
    };


    $scope.changeCount = function (title, delta) {
        $http({
            url: contextPath + '/products/order/changeCount',
            method: 'POST',
            params: {
                title: title,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadOrders();
        }).catch(function (err) {
            return errorService.handleError(error);
        });
    }

    $scope.addProductToOrders = function (id) {
       $http({
           url: contextPath + '/products/order/add',
           method: 'POST',
           params: {
               id: id
           }
       }).then(function (response) {
           console.log(response)
           $scope.loadOrders();
       });
    }

    $scope.loadProducts();
    $scope.loadOrders();
});