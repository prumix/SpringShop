angular.module('app', ['ui.bootstrap']).controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/app/api/v1';


    $scope.currentPage = 1;

    $scope.setPage = function (pageNo) {
        $scope.currentPage = pageNo;
    };

    $scope.pageChanged = function () {
        $scope.loadProducts($scope.currentPage)
    };


    $scope.loadProducts = function (pageIndex = $scope.currentPage) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                p: pageIndex,
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
            $scope.totalItems = response.data.totalElements;
        });
    };


    $scope.loadOrders = function () {
        $http.get(contextPath + '/products/order')
            .then(function (responce) {
                $scope.OrderList = responce.data;
            });
    };


    $scope.changeCount = function (index, delta) {
        $http({
            url: contextPath + '/products/order/changeCount',
            method: 'POST',
            params: {
                index: index,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadOrders();
        }).catch(function (err) {
            return errorService.handleError(error);
        });
    }

    $scope.addProductToOrders = function (p) {
        $http({
            url: contextPath + '/products/order/add',
            method: 'POST',
            params: {
                id: p.id,
                title: p.title,
                cost: p.cost
            }
        }).then(function (response) {
            $scope.loadOrders();
        });
    }


    $scope.loadProducts();
    $scope.loadOrders();
})
;