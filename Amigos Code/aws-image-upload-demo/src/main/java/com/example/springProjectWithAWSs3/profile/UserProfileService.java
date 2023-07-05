package com.example.springProjectWithAWSs3.profile;

import com.example.springProjectWithAWSs3.bucket.BucketName;
import com.example.springProjectWithAWSs3.filestore.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.*;

@Service

public class UserProfileService {
    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;
    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles(){
        return userProfileDataAccessService.getUserProfile();
    }


    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        isFileEmpty(file);

        isImage(file);

        UserProfile user = getUserProfileOrThrow(userProfileId);

        Map<String, String> metadata = extractMetadata(file);

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path,filename,Optional.of(metadata), file.getInputStream());
            user.setUserProfileImageLink(filename);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    private static Map<String, String> extractMetadata(MultipartFile file) {
        Map<String,String> metadata = new HashMap<>();

        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUserProfileOrThrow(UUID userProfileId) {

        return userProfileDataAccessService
                .getUserProfile()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
    }

    private static void isImage(MultipartFile file) {
        if(!Arrays.asList(MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(file.getContentType())){
            throw new IllegalStateException("file must be an image { " + file.getContentType() + " }" );
        }
    }

    private static void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()){
            throw new IllegalStateException("can't upload empty file [ " + file.getSize() + " ]" );
        }
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile user = getUserProfileOrThrow(userProfileId);
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        return user.getUserProfileImageLink()
                .map(key -> fileStore.download(path,key))
                .orElse(new byte[0]);


    }
}
