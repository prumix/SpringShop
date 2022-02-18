angular.module('market-front').controller('welcomeController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555';

    $scope.loadStatCart = function (){
        $http.get(contextPath + '/stat/api/v1/statistic/cart')
            .then(function (response) {
                $scope.statCart = response.data;
                console.log(response.data)
            });
    }

    $scope.loadStatOrder = function (){
        $http.get(contextPath + '/stat/api/v1/statistic/order')
            .then(function (response) {
                $scope.statOrder = response.data;
                console.log(response.data)
            });
    }


    $scope.loadStatCart();
    $scope.loadStatOrder();

});