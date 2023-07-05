package com.example.springProjectWithAWSs3.profile;

import com.example.springProjectWithAWSs3.datastore.FackUserProfileDataStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileDataAccessService {

    private final FackUserProfileDataStore fackUserProfileDataStore;

    @Autowired
    public UserProfileDataAccessService(FackUserProfileDataStore fackUserProfileDataStore) {
        this.fackUserProfileDataStore = fackUserProfileDataStore;
    }

    List<UserProfile> getUserProfile(){
        return fackUserProfileDataStore.getUserProfileList();
    }


}
