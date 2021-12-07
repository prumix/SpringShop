angular.module('app', []).controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/app';

    $scope.loadProducts = function () {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    };

    $scope.findProductById = function(id) {
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

    $scope.changeCost = function (id, delta) {
        $http({
            url: contextPath + '/products/change_cost',
            method: 'POST',
            params: {
                id: id,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadProducts();
        }).catch(function (err) {
            return errorService.handleError(error);});
    }

    $scope.filteredProducts = function(min, max) {
        $http({
            url: contextPath + '/products/filter_by_cost',
            method: 'GET',
            params: {
                min: min,
                max: max
            }
        }).then(function (response) {
            $scope.ProductsList = response.data;
        });
    }

    $scope.goToPreviousPage = function() {
        $http({
            url: contextPath + '/products/previous',
            method: 'GET',
        }).then(function (response) {
            $scope.ProductsList = response.data;
        });
    }

    $scope.goToNextPage = function() {
        $http({
            url: contextPath + '/products/next',
            method: 'GET',
        }).then(function (response) {
            $scope.ProductsList = response.data;
        });
    }

    $scope.loadProducts();
});