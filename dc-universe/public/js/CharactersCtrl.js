var app = angular.module('DCUniverseOntoMap');

app.controller('CharactersCtrl', ['$scope', '$uibModal', 'charactersService', '$http', function($scope, $uibModal, charactersService, $http) {
  $scope.characters = [];
  $scope.loaded = false;
  $scope.nameFilter = "";

  $scope.getCharactersSuccessCallback = function(data) {
    var tmp = [];
    for(var cid in data) {
      tmp.push(data[cid]);
    }
    $scope.characters = tmp;
    $scope.loaded = true;
  };

  $scope.tmp = charactersService.getCharacters($scope.getCharactersSuccessCallback);

  // Slider for filtering characters
  // DC Comics from 1938 - Today year
  $scope.year = new Date().getFullYear();
  $scope.isAgeFilterActive = false;

  $scope.ageSlider = {
    min: 1938,
    max: $scope.year,
    options: {
      floor: 1938,
      ceil: $scope.year,
      showSelectionBar: true
    }
  };

  $scope.filterCharacters = function(c) {
    var details = true;
    var powers = true;
    if(c.name) details = c.name.toUpperCase().match($scope.nameFilter.toUpperCase());
    if(c.powers) details = details || c.powers.toUpperCase().match($scope.nameFilter.toUpperCase());

    if($scope.isAgeFilterActive===false) {
      return details;
    }
    else {
      return (details && parseInt(c.creationYear) >= $scope.ageSlider.min && parseInt(c.creationYear) <= $scope.ageSlider.max);
    }
  };

  $scope.consultCharacter = function(charID) {
    if(!$scope.characters) {
      alert("Impossible to consult character.");
      return;
    }
    var res = $scope.characters.filter(function(c){ return charID===c.id; });
    charactersService.setCurrentCharacter(res[0]);
    $scope.newConsultCharacterModal();
  };

  $scope.newConsultCharacterModal = function() {
    var modalInstance = $uibModal.open({
      animation: true,
      templateUrl: 'views/character-consult-modal.html',
      controller: 'ConsultCharacterModalCtrl',
      size: 'md'
    });
  };

}]);

// Consult the character detail
app.controller('ConsultCharacterModalCtrl', ['$scope', '$uibModalInstance', 'charactersService', function($scope, $uibModalInstance, charactersService) {
  $scope.character = charactersService.getCurrentCharacter();
  $scope.year = new Date().getFullYear();
  $scope.getAge = function(creationYear) {
    return ($scope.year - parseInt(creationYear));
  };
  $scope.closeModal = function () {
    $uibModalInstance.dismiss('cancel');
  };
}]);
