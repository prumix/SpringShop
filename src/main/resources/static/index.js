angular.module('app', [])
    .controller('indexController', function ($scope, $http) {
        const contextPath = 'http://localhost:8080/app';
        $scope.curPage = 1;
        $scope.itemsPerPage = 3;
        $scope.maxSize = 5;
        $scope.sortKey = 'cost';
        $scope.sortReverse = false;

        $scope.loadProducts = function () {
            $http.get(contextPath + '/products')
                .then(function (response) {
                    $scope.ProductsList = response.data;
                });
        };

        $scope.deleteProduct = function (id) {
            $http.get(contextPath + '/products/delete/' + id)
                .then(function (response) {
                    $scope.loadProducts();
                });
        }

        $scope.sort = function (key) {
            $scope.sortReverse = ($scope.sortKey == key) ? !$scope.sortReverse : $scope.sortReverse;
            $scope.sortKey = key;
        }


        /*   $scope.changeCost = function (id, delta) {
               $http({
                   url: contextPath + '/products/change_cost',
                   method: 'GET',
                   params: {
                       id: id,
                       delta: delta
                   }
               }).then(function (response) {
                   $scope.loadProducts();
               });
           }*/

        $scope.loadProducts();
    });