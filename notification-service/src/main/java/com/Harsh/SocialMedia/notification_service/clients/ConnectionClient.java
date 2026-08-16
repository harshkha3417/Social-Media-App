package com.Harsh.SocialMedia.notification_service.clients;

import com.Harsh.SocialMedia.notification_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name="connections-service",path="/connections")
public interface ConnectionClient {
    @GetMapping("/core/first-degree")
    public List<PersonDto> getFirstConnection(@RequestHeader("X-User-Id")Long userId);

    @GetMapping("/second-degree")
    public List<PersonDto> getSecondConnection(@RequestHeader("X-User-Id")Long userId);

    @GetMapping("/third-degree")
    public List<PersonDto> getThirdConnection(@RequestHeader("X-User-Id")Long userId);
}
