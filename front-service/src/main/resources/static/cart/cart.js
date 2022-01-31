angular.module('market-front').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/cart/';

    let cartDto;

    $scope.loadCart = function () {
        $http({
            url: contextPath + 'api/v1/cart/' + $localStorage.springWebGuestCartId,
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
            cartDto = $scope.cart;
        });
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.clearCart = function () {
        $http.get(contextPath + 'api/v1/cart/' + $localStorage.springWebGuestCartId + '/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.checkOut = function () {
        let orderDetailsDto = $scope.orderDetails;


        let cartAndOrderDetails = {
            orderDetailsDto,
            cartDto
        }
        console.log($scope.cartAndOrderDetails)
        $http.post('http://localhost:5555/core/api/v1/orders', cartAndOrderDetails)
            .then(function (response) {
                $scope.clearCart();
                $scope.orderDetails = null
            });
    };

    $scope.loadCart();
});