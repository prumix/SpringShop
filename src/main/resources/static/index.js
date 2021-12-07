angular.module('app', []).controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/app';

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null
            }
        }).then(function (response) {
            $scope.ProductsList = response.data.content;
        });

    };



    $scope.findProductById = function (id) {
        $http.get(contextPath + '/products/' + id)
            .then(function (response) {
                $scope.ProductsList = response.data;
                console.log(response.data);
            });
    }

    $scope.deleteProduct = function (id) {
        $http.get(contextPath + '/products/delete/' + id)
            .then(function (response) {
                $scope.loadProducts();
            });
    }


    $scope.loadProducts();
});