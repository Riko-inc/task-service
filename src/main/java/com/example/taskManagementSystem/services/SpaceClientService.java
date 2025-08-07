package com.example.taskManagementSystem.services;
import com.example.taskManagementSystem.config.FeignConfig;
import com.example.taskManagementSystem.domain.events.dto.SpaceMemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "space-service", url = "http://space-service:${SERVICE_PORT:8085}/api/v1/", configuration = FeignConfig.class)
public interface SpaceClientService {

    @GetMapping("/spaces/{workspaceId}/users")
    SpaceMemberDto findUserBySpaceId(@PathVariable("workspaceId") Long workspaceId);
}
