package com.skypro.course_03.controllers;

import com.skypro.course_03.entity.Avatar;
import com.skypro.course_03.services.AvatarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping(path = "/avatar")
@Tag(name = "Avatars UI", description = "Check your avatar methods.")
public class AvatarController {

    private AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Avatar>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(avatarService.findById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@RequestParam Long studentId, @RequestParam MultipartFile avatar) {
        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big.");
        }
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

}
