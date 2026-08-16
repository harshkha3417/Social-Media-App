package com.Harsh.SocialMedia.connection_service.controller;

import com.Harsh.SocialMedia.connection_service.entity.Person;
import com.Harsh.SocialMedia.connection_service.service.ConnectionsService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionsController {
    private final ConnectionsService connectionsService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstConnection(){
        return ResponseEntity.ok(connectionsService.getFirstDegreeConnections());
    }
    @GetMapping("/second-degree")
    public ResponseEntity<List<Person>> getSecondConnection(){
        return ResponseEntity.ok(connectionsService.getSecondDegreeConnections());
    }
    @GetMapping("/third-degree")
    public ResponseEntity<List<Person>> getThirdConnection(){
        return ResponseEntity.ok(connectionsService.getThirdDegreeConnections());
    }
    @PostMapping("/request/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionsService.sendConnectionRequest(userId));
    }
    @PostMapping("/accept/{userId}")
    public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionsService.acceptConnectionRequest(userId));
    }
    @PostMapping("/reject/{userId}")
    public ResponseEntity<Boolean> rejectConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionsService.rejectConnectionRequest(userId));
    }
}
