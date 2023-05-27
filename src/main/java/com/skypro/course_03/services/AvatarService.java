package com.skypro.course_03.services;

import com.skypro.course_03.entity.Avatar;
import com.skypro.course_03.entity.Student;
import com.skypro.course_03.exceptions.AvatarProcessingException;
import com.skypro.course_03.exceptions.StudentNotFoundException;
import com.skypro.course_03.repositories.AvatarRepository;
import com.skypro.course_03.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    private final String avatarDir;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository,
                         @Value("${avatar.folder.url}") String avatarDir) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarDir = avatarDir;
    }

    public void upload(Long studentId, MultipartFile multipartFile) {
        try {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(StudentNotFoundException::new);
            String fileName = String.format("%d.%s",
                    student.getId(),
                    StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()));
            Path path = Paths.get(avatarDir, fileName);
            byte[] data = multipartFile.getBytes();
            Files.write(path, data);

            Avatar avatar = new Avatar();
            avatar.setData(data);
            avatar.setFilePath(path.toString());
            avatar.setFileSize(data.length);


        } catch (IOException e) {
            throw new AvatarProcessingException();
        }

    }
}
