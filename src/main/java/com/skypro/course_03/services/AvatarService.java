package com.skypro.course_03.services;

import com.skypro.course_03.entity.Avatar;
import com.skypro.course_03.entity.Student;
import com.skypro.course_03.exceptions.AvatarProcessingException;
import com.skypro.course_03.exceptions.StudentNotFoundException;
import com.skypro.course_03.repositories.AvatarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Transactional
public class AvatarService {

    private String avatarDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentService studentService,
                         @Value("${students.avatar.dir.path}") String avatarDir) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
        this.avatarDir = avatarDir;
    }

    public Optional<Avatar> findById(Long id) {
        return avatarRepository.findById(id);
    }

    public void uploadAvatar(Long studentId, MultipartFile multipartFile) {
        try {
            Student student = studentService.getById(studentId)
                    .orElseThrow(StudentNotFoundException::new);
            String fileName = String.format(
                    "%d.%s",
                    student.getId(),
                    StringUtils.getFilenameExtension(multipartFile.getOriginalFilename())
            );
            byte[] data = multipartFile.getBytes();
            Path path = Paths.get(avatarDir, fileName);
            Files.write(path, data);

            Avatar avatar = new Avatar();
            avatar.setData(data);
            avatar.setFilePath(path.toString());
            avatar.setFileSize(data.length);
            avatar.setStudent(student);
            avatar.setMediaType(multipartFile.getContentType());
            avatarRepository.save(avatar);

        } catch (IOException e) {
            throw new AvatarProcessingException();
        }
    }
}
