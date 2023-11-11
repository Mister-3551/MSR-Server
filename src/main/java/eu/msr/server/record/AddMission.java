package eu.msr.server.record;

import eu.msr.server.security.validator.anno.*;
import org.springframework.web.multipart.MultipartFile;

public record AddMission(
        @ValidName
        String name,
        @ValidDescription
        String description,
        @ValidImage
        MultipartFile image,
        @ValidMap
        MultipartFile map,
        @ValidMoney
        float price,
        @ValidTime
        String bestTime,
        @ValidTime
        String deadline) {
}