package com.example.springProjectWithAWSs3.datastore;

import com.example.springProjectWithAWSs3.profile.UserProfile;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This is project we are not using any databases. so we are using in house repository like this.
 * if we use database we don't need to create same uuid again and again
 */
@Repository
public class FackUserProfileDataStore {


    private static final List<UserProfile> USER_PROFILE_LIST = new ArrayList<>();
    static{
        USER_PROFILE_LIST.add(new UserProfile(UUID.fromString("b1266be1-a802-4bd6-a329-f8bcacaeb8e7"), "Shubiksha", null));
        USER_PROFILE_LIST.add(new UserProfile(UUID.fromString("1fefcb14-9a7a-4a47-80a0-e2c909c89d79"), "Priya", null));
    }

    public List<UserProfile> getUserProfileList(){
        return USER_PROFILE_LIST;
    }
}


/*
0
:
{userProfileId: 'bee1fbf2-a091-4350-8584-4dc8bc666b2e', username: 'Shubiksha', userProfileImageLink: null}
1
:
{userProfileId: 'f4181b51-9836-41d5-8b57-6ad8544106e7', username: 'Priya', userProfileImageLink: null}
length
:
2
* */