'use strict';
angular.module('prova')
.service('Start', function () {
  console.log('Hello from your Start service');

  // some initial data
  this.someData = {
    binding: 'Yes! Got that databinding working'
  };

  // TODO: do your service thing
});
