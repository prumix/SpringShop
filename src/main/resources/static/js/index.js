angular.module('app', ['ui.bootstrap', 'ngStorage']).controller('productController',
    function ($scope, $rootScope, $http, $localStorage) {
        const contextPath = 'http://localhost:8080/app/api/v1';

        if ($localStorage.springWebUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        }

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
                    min_price: $scope.filter ? $scope.filter.min_price : null,
                    max_price: $scope.filter ? $scope.filter.max_price : null
                }
            }).then(function (response) {
                $scope.ProductsPage = response.data;
                $scope.totalItems = response.data.totalElements;
            });
        };

        $scope.addToCart = function (productId) {
            $http.get('http://localhost:8080/app/api/v1/carts/add/' + productId)
                .then(function (response) {
                    $scope.loadCart();
                });
        }

        $scope.clearCart = function () {
            $http.get('http://localhost:8080/app/api/v1/carts/clear')
                .then(function (response) {
                    $scope.loadCart();
                });
        }

        $scope.loadCart = function () {
            $http.get('http://localhost:8080/app/api/v1/carts')
                .then(function (response) {
                    $scope.Cart = response.data;
                });
        }

        $scope.createOrder = function (){
            var postData = {
                'cart': $scope.Cart,
                'userName': $localStorage.springWebUser.username
            }
            $http.post('http://localhost:8080/app/api/v1/orders', angular.toJson(postData))
                .then(function (response){
                    alert('Заказ оформлен')
                    $scope.clearCart();
                })
        }

        $scope.changeCount = function (username,id, delta) {
            $http({
                url: contextPath + '/order/changeCount',
                method: 'POST',
                params: {
                    username: username,
                    id: id,
                    delta: delta
                }
            }).then(function (response) {
                $scope.loadOrders();
            }).catch(function (err) {
                return errorService.handleError(error);
            });
        }

        $scope.tryToAuth = function () {
            $http.post('http://localhost:8080/app/auth', $scope.user)
                .then(function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                        $scope.user.username = null;
                        $scope.user.password = null;
                    }
                }, function errorCallback(response) {
                });
        };

        $scope.tryToLogout = function () {
            $scope.clearUser();
            if ($scope.user.username) {
                $scope.user.username = null;
            }
            if ($scope.user.password) {
                $scope.user.password = null;
            }
        };

        $scope.clearUser = function () {
            delete $localStorage.springWebUser;
            $http.defaults.headers.common.Authorization = '';
        };

        $rootScope.isUserLoggedIn = function () {
            if ($localStorage.springWebUser) {
                $scope.username = $localStorage.springWebUser.username;
                return true;
            } else {
                return false;
            }
        };

        $scope.showCurrentUserInfo = function () {
            $http.get('http://localhost:8080/app/api/v1/profile')
                .then(function successCallback(response) {
                    alert('MY NAME IS: ' + response.data.username);
                }, function errorCallback(response) {
                    alert('UNAUTHORIZED');
                });
        }


        $scope.loadProducts();
        $scope.loadCart();
    })
;