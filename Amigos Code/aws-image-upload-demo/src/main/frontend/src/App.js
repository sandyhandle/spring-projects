import React, {useState,useEffect,useCallback} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import {useDropzone} from 'react-dropzone'

const UserProfile = () => {

  const [userProfile, setUserProfile] = useState([]);

  const fetchUserPfifiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then(res => {
      console.log(res);
      const data = res.data;
      setUserProfile(res.data);
    });
  }; 

  useEffect(() => {
    fetchUserPfifiles();
  }, []);
  
  return userProfile.map((userProfile,index) => {
    return (
      <div key={index}>
        {userProfile.userProfileId ?( 
          <img
            src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/image/download`}
          />
        ) : null}
        <br/>
        <br/>
        <h1>{userProfile.username}</h1>
        <p>{userProfile.userProfileId}</p>
        <MyDropzone {...userProfile} />
        <br/>
      </div>
    )
  })
};

function MyDropzone({userProfileId}) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];

    console.log(file);

    const formData = new FormData();
    formData.append("file",file);

    axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,formData,{
      headers:{
        "Content-Type": "multipart/form-data"
      }
    }).then(() => {
      console.log("file uploaded successfully");
      
    }).catch(err => {
      console.log(err);
    });

  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop some profile image, or click to select profile image</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <UserProfile/>
    </div>
  );
}

export default App;
