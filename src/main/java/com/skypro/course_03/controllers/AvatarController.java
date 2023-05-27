package com.skypro.course_03.controllers;

import com.skypro.course_03.services.AvatarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("avatar")
public class AvatarController{

    private AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping()
    public ResponseEntity<String> upload(@RequestParam Long studentId, @RequestParam MultipartFile multipartFile) {
        avatarService.upload(studentId, multipartFile);
        return ResponseEntity.ok().build();
    }
}
