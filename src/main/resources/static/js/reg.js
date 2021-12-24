angular.module('appReg', []).controller('regController',
    function ($scope, $rootScope, $http) {
        const contextPath = 'http://localhost:8080/app/api/v1';


        $scope.registration = function (user) {
            $http.post('http://localhost:8080/app/api/v1/registration', user)
                .then(function (response){
                    console.log(response)
                })
        }
    });