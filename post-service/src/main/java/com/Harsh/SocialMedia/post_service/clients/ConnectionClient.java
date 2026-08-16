package com.Harsh.SocialMedia.post_service.clients;

import com.Harsh.SocialMedia.post_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name="connections-service",path="/connections")
public interface ConnectionClient {
    @GetMapping("/core/first-degree")
    public List<PersonDto> getFirstConnection();
    @GetMapping("/core/second-degree")
    public List<PersonDto> getSecondConnection();

    @GetMapping("/core/third-degree")
    public List<PersonDto> getThirdConnection();
}
