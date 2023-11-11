package eu.msr.server.controller;

import eu.msr.server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/images/profile/{name}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable("name") String name) throws Exception {
        return fileService.profileImage(name);
    }

    @GetMapping("/images/other/{name}")
    public ResponseEntity<byte[]> getOtherImage(@PathVariable("name") String name) throws Exception {
        return fileService.otherImage(name);
    }
}