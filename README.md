# SFTP Java App 

This application allows you to manage files using SFTP. It utilizes Docker for the SFTP server and a Java-based client for file operations.

# Setup

## Setting up Docker SFTP Server

Use the [atmoz/sftp](https://hub.docker.com/r/atmoz/sftp) docker image to create a container.

```bash
docker run -p 22:22 -d atmoz/sftp foo:pass:::upload
```
where:

- foo is the username
- pass is the password
- upload is the directory to which the SFTP user has access

## Setting Up SSH keys
Generate a new SSH key pair:

```bash
ssh-keygen -t rsa -f ~/.ssh/id_rsa_sftp -C "key_name"
```
Next, you need to copy your public key to the Docker container's authorized keys. First, login to your Docker container and create a file named authorized_keys in the upload directory:

```bash
docker exec -it <container_id> /bin/bash
cd /home/foo/upload
touch authorized_keys
exit
```

Now, copy the public key to the Docker container's authorized_keys:

```bash
docker cp ~/.ssh/id_rsa_sftp.pub <container_id>:/home/foo/upload/authorized_keys
```

You can find your container id by running 
```bash
docker ps
```
## Cloning Repository and Setting Up Config File

Clone the repository:
```bash
git clone <repository_url>
```
Navigate to the resources directory:
```bash
cd SFTPjavaApp/app/src/main/resources/
```
Create a new config.properties file:
```bash
cp example.config.properties config.properties
```
Open config.properties and edit it with your details:
```bash
username=USERNAME
password=PASSWORD
uploadDir=REMOTE_DIRECTORY
privateKeyPath=LOCAL_PRIVATE_SSH_KEY_PATH
job=JOB_TYPE
localDir=ABSOLUTE_PATH_FOR_LOCAL_DIRECTORY
localFile=FILE_NAME
remoteFile=FILE_NAME
```

Currently only supports "**upload**", "**download**" job types

Example:
```bash
username=foo
password=pass
uploadDir=upload
privateKeyPath=C:/Users/natek/.ssh/id_rsa_sftp
job=upload
localDir=D:/projects/SFTPjavaApp/app/src/main/resources/
localFile=test1.txt
remoteFile=upload.txt
```
