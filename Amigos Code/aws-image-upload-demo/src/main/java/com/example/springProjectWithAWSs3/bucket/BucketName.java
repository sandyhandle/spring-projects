package com.example.springProjectWithAWSs3.bucket;

public enum BucketName {
    PROFILE_IMAGE("your profile name");

    private final String bucketName;

    BucketName(String bucketName){
        this.bucketName = bucketName;
    }

    public String getBucketName(){
        return bucketName;
    }

}
