package com.example.billing;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// Force HTTPS for now because of some strangeness
// around how Eureka hands out ip:ports on RBG.
// At some point remove this and see what happens.
@FeignClient("https://billing")
public interface Client {
    @RequestMapping(method = RequestMethod.POST, value = "/reocurringPayment", consumes = "application/json")
    void billUser(BillingRequest request);
}
