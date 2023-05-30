# SFTP Java App 

This application allows you to manage files using SFTP. It utilizes Docker for the SFTP server and a Java-based client for file operations.

# Setup

## Setting up Docker SFTP Server

Use the [atmoz/sftp](https://hub.docker.com/r/atmoz/sftp) docker image to create a container.

```bash
docker run -p <host port>**:22 -d atmoz/sftp <username>:<password>:::upload
```
In my example, I use:

- **foo** is the username
- **pass** is the password
- **upload** is the directory to which the SFTP user has access
- you can use other host port instead of **2222**

Ex: docker run -p 2222:22 -d atmoz/sftp foo:pass:::upload

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
cd sftp-java-program-with-apache-commons1/app/src/main/resources/
```
Create a new config.properties file:
```bash
cp example.config.properties config.properties
```
Open config.properties and edit it with your details:
```bash
username=DOCKER_USERNAME
password=DOCKER_PASSWORD
hostPort=HOST_PORT
privateKeyPath=SSH_PRIVATE_KEY_PATH (including file name)
job= download_dir / upload_dir / upload_file / download_file / get_file_names

remoteDir=REMOTE_DIRECTORY_PATH
localDir=LOCAL_DIRECTORY_PATH
fileName=FILENAME
filesOnly= no / yes
```

Currently supports:

**Job type / config properties that has to be set** :

- upload_file / fileName, localDir, remoteDir
- download_file / fileName, localDir, remoteDir
- upload_dir / remoteDir, localDir, filesOnly
- download_dir / remoteDir, localDir, filesOnly
- get_file_names / remoteDir

Example:
```bash
username=foo
password=pass
remoteDir=upload
privateKeyPath=C:/Users/natek/.ssh/id_rsa_sftp
job=get_file_names
localDir=D:/projects/sftp-java-program-with-apache-commons1/app/src/main/resources/
localFile=test.txt
remoteFile=upload.txt
hostPort=2222
```
## Build and Run

Navigate to the cloned repository 
```bash
cd sftp-java-program-with-apache-commons1
```
Run when job type and properties are set:

```bash
gradle run
```
