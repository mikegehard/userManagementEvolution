describe("UserSubscription", function () {
    var client;

    beforeEach(function () {
        client = new UserSubscription('http://localhost:1234');

        userSubscriptionProvider = Pact.mockService({
            consumer: 'JavaScriptClient',
            provider: 'UMS',
            port: 1234,
            done: function (error) {
                expect(error).toBe(null);
            }
        });
    });

    it("should subscribe a user", function (done) {

        userSubscriptionProvider
            .given("a userId 1234 and a packageId 8181")
            .uponReceiving("a subscription to create")
            .withRequest(
                "post",
                "/subscriptions",
                {"Accept": "application/json", "Content-Type": "application/json"},
                {"userId": "1234", "packageId": "8181"}
            )
            .willRespondWith(
                201,
                {},
                {"acknowledged": true}
            );

        userSubscriptionProvider.run(done, function (runComplete) {

            var promise = client.create();
            var result;

            promise.done(function (data) {
                result = 'success';
            }).fail(function () {
                    result = 'fail';
                });

            expect(result).toEqual("success");
            runComplete();
        });
    });
});
