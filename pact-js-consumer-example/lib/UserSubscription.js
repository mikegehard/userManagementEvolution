function UserSubscription(){

  return {
    create: function(){
      return $.ajax({ url: "http://localhost:1234/subscriptions",
               data: JSON.stringify({ userId: "1234", packageId: "8181" }),
               method: 'POST',
               async: false,
               headers: {"Accept": "application/json", "Content-Type": "application/json"} })
    }
  }
};
