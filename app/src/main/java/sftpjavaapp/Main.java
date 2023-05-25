/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package sftpjavaapp;

import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.sftp.IdentityInfo;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Main {
    private static String username;
    private static String password;
    private static String uploadDir;
    private static String localDir;

    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));

            username = properties.getProperty("username");
            password = properties.getProperty("password");
            uploadDir = properties.getProperty("uploadDir");
            localDir = properties.getProperty("localDir");

            String privateKeyPath = properties.getProperty("privateKeyPath");
            String job = properties.getProperty("job");
            String localFile = properties.getProperty("localFile");
            String remoteFile = properties.getProperty("remoteFile");

            FileSystemOptions fsOptions = new FileSystemOptions();
            File privateKeyFile = new File(privateKeyPath);
            IdentityInfo identityInfo = new IdentityInfo(privateKeyFile);

            SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fsOptions, "no");
            SftpFileSystemConfigBuilder.getInstance().setIdentityInfo(fsOptions, identityInfo);

            if (job.equals("upload")) {
                manageFile(localFile, remoteFile, true);
            } else if (job.equals("download")) {
                manageFile(localFile, remoteFile, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void manageFile(String localFile, String remoteFile, boolean isUpload) {
        try {
            FileSystemManager manager = VFS.getManager();

            FileObject local = manager.resolveFile(System.getProperty("user.dir") + "/" + localDir + localFile);
            FileObject remote = manager.resolveFile("sftp://" + username + ":" + password + "@localhost:2222/" + uploadDir + "/" + remoteFile);

            if (isUpload) {
                remote.copyFrom(local, Selectors.SELECT_SELF);
            } else {
                local.copyFrom(remote, Selectors.SELECT_SELF);
            }

            local.close();
            remote.close();
            manager.close();
        } catch (FileSystemException e) {
            e.printStackTrace();
        }
    }
}

