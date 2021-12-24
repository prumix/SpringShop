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
                    min_cost: $scope.filter ? $scope.filter.min_cost : null,
                    max_cost: $scope.filter ? $scope.filter.max_cost : null
                }
            }).then(function (response) {
                $scope.ProductsPage = response.data;
                $scope.totalItems = response.data.totalElements;
            });
        };





        $scope.loadOrders = function () {
            let username = $localStorage.springWebUser.username;
            $http({
                url: contextPath + '/order',
                method: 'GET',
                params:{
                    username: username
                }
            }).then(function (response){
                $scope.OrderList = response.data;
                console.log(response)
            })
        };


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

        $scope.addProductToOrders = function (p) {
            let username = $localStorage.springWebUser.username
            $http({
                url: contextPath + '/order/add',
                method: 'POST',
                params: {
                    id: p.id,
                    username: username,
                    title: p.title,
                    cost: p.cost
                }
            }).then(function (response) {
                $scope.loadOrders();
                console.log(response)
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
        $scope.loadOrders();
    })
;