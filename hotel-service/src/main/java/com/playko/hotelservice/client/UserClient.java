package com.playko.hotelservice.client;

import com.playko.hotelservice.client.dto.User;
import com.playko.hotelservice.client.interceptor.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-service",
        url = "http://localhost:8091/users/v1",
        configuration = FeignClientInterceptor.class)
public interface UserClient {

    @GetMapping("/admin/getOwner/{id}")
     Optional<User> getOwner(@PathVariable Long id);

    @GetMapping("/owner/getEmployee/{id}")
    Optional<User> getEmployee(@PathVariable Long id);

    @GetMapping("/employee/getClient/{id}")
    Optional<User> getClient(@PathVariable Long id);
}
