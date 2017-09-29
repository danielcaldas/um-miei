var app = angular.module('DCUniverseOntoMap');

app.factory('charactersService', ['$http', function($http) {
  var service = {};
  service.currentCharacter = {};

  service.getCharacters = function(successCallback) {
    return $http.get("/dc/characters").success(function(data){
      return successCallback(data);
    });
  };

  service.setCurrentCharacter = function(c) {
    service.currentCharacter = c;
  };

  service.getCurrentCharacter = function() {
    return service.currentCharacter;
  };

  return service;
}]);
