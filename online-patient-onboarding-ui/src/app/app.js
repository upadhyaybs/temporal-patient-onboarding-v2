var app = angular.module("NewPatientOnboarding", []);

app.controller("NewPatientOnboardingController", function ($scope, $http) {
  $scope.onboardedpatients = [];
  $scope.currentid = "";
  $scope.lastmessage = "";

  $scope.interval;

  $scope.form = {
    name: "",
    zip: "",
    condition: "",
    email: "",
    phone: "",
    contactmethod: "",
  };

  // Invoke workflow execution
  $scope.add = function () {
    $scope.currentid = getUniqueId();
    let newpatientdata = {
      id: $scope.currentid,
      name: $scope.form.name,
      zip: $scope.form.zip,
      age: "",
      insurance: " ",
      insuranceId: " ",
      condition: $scope.form.condition,
      email: $scope.form.email,
      phone: $scope.form.phone,
      contactMethod: $scope.form.contactmethod,
    };

    $scope.interval = setInterval(updateMessage, 1000);

    $http({
      method: "POST",
      url: "http://localhost:8083/onboard",
      data: newpatientdata,
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
        "Access-Control-Allow-Origin": "*",
      },
    }).then(_success, _error);
  };

  function _success(response) {
    clearInterval($scope.interval);
    if (response.data.onboarded == "no") {
      swal({
        title: "Unable to onboard patient",
        text: "",
        icon: "error",
        timer: 3000,
      });
    }
    $scope.onboardedpatients.push(response.data);
    _clearForm();
  }

  function _error(response) {
    clearInterval($scope.interval);
    alert(response.statusText);
  }

  function _clearForm() {
    $scope.form.name = "";
    $scope.form.zip = "";
    $scope.form.condition = "";
    $scope.form.email = "";
    $scope.form.phone = "";
    $scope.form.contactmethod = "";
  }

  function getUniqueId() {
    return Math.random().toString(36).substr(2, 6);
  }

  function updateMessage() {
    fetch(
      "http://localhost:8083/onboard?" +
        new URLSearchParams({
          id: $scope.currentid,
        })
    )
      .then((response) => response.text())
      .then((message) => {
        if (
          message == $scope.lastmessage ||
          message.trim() === "" ||
          message.startsWith("Unable")
        ) {
          // nothing....
        } else {
          sicon = "success";
          if (message.startsWith("Compensating")) {
            sicon = "info";
          }
          $scope.lastmessage = message;
          toShowMessage = message.replace(/message=/g, "");
          toShowMessage = decodeURIComponent(
            (toShowMessage + "").replace(/\+/g, "%20")
          );
          swal({
            title: toShowMessage,
            text: "",
            icon: sicon,
            timer: 5000,
            button: "",
          });
        }
      });
  }
});
