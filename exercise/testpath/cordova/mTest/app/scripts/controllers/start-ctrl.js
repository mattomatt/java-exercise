'use strict';
angular.module('prova')
.controller('StartCtrl', function ($scope, Start) {
  console.log('Hello from your StartCtrl controller. This is your scope:', $scope);

  // bind data from service
  $scope.someData = Start.someData;

  // TODO: do your controller thing
});
