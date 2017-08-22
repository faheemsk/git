/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.util;

import com.optum.oss.constants.ApplicationConstants;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class CheckSecureFileCopy {
    
    private static final Logger logger = Logger.getLogger(CheckSecureFileCopy.class);
    
    private boolean isInSecureDir(Path file) {
        return isInSecureDir(file, null);
    }

    private boolean isInSecureDir(Path file, UserPrincipal user) {
        return isInSecureDir(file, user, 5);
    }

    /**
     * Indicates whether file lives in a secure directory relative to the
     * program's user
     *
     * @param file Path to test
     * @param user User to test. If null, defaults to current user
     * @param symlinkDepth Number of symbolic links allowed
     * @return true if file's directory is secure.
     */
    private boolean isInSecureDir(Path file, UserPrincipal user,
            int symlinkDepth) {
        String osName = this.getOperatingSystem();
        if (!file.isAbsolute()) {
            file = file.toAbsolutePath();
        }
        if (symlinkDepth <= 0) {
            // Too many levels of symbolic links
            return false;
        }

        // Get UserPrincipal for specified user and superuser
        FileSystem fileSystem
                = Paths.get(file.getRoot().toString()).getFileSystem();
        UserPrincipalLookupService upls
                = fileSystem.getUserPrincipalLookupService();
        UserPrincipal root = null;
        try {
            if(!osName.equalsIgnoreCase(ApplicationConstants.OS_WINDOWS))
            {
                root = upls.lookupPrincipalByName("root");
            }
            else
            {
                root = upls.lookupPrincipalByName(System.getProperty("user.name"));
            }
            if (user == null) {
                user = upls.lookupPrincipalByName(System.getProperty("user.name"));
            }
            if (root == null || user == null) {
                return false;
            }
        } catch (IOException x) {
            return false;
        }

        // If any parent dirs (from root on down) are not secure,
        // dir is not secure
        for (int i = 1; i <= file.getNameCount(); i++) {
            Path partialPath = Paths.get(file.getRoot().toString(),
                    file.subpath(0, i).toString());

            try {
                if (Files.isSymbolicLink(partialPath)) {
                    if (!isInSecureDir(Files.readSymbolicLink(partialPath),
                            user, symlinkDepth - 1)) {
                        // Symbolic link, linked-to dir not secure
                        return false;
                    }
                } else {
                    UserPrincipal owner = Files.getOwner(partialPath);
                    //if (!user.equals(owner) && !root.equals(owner)) {
                    if (!user.equals(owner)) {
                        // dir owned by someone else, not secure
                        return false;
                    }
                    
                    if(!osName.equalsIgnoreCase(ApplicationConstants.OS_WINDOWS))
                    {
                        PosixFileAttributes attr
                                = Files.readAttributes(partialPath, PosixFileAttributes.class);
                        Set<PosixFilePermission> perms = attr.permissions();
                        if (perms.contains(PosixFilePermission.GROUP_WRITE)
                                || perms.contains(PosixFilePermission.OTHERS_WRITE)) {
                            // Someone else can write files, not secure
                            return false;
                        }
                    }
               }
            } catch (IOException x) {
                return false;
            }
        }

        return true;
    }
    
    
    private String getOperatingSystem() {
        String retOsName = "";
        String osName = System.getProperty("os.name");
        String osNameMatch = osName.toLowerCase();
        if (osNameMatch.contains("linux")) {
            retOsName = ApplicationConstants.OS_LINUX;
        } else if (osNameMatch.contains("windows")) {
            retOsName = ApplicationConstants.OS_WINDOWS;
        } else if (osNameMatch.contains("solaris") || osNameMatch.contains("sunos")) {
            retOsName = ApplicationConstants.OS_SOLARIS;
        } else if (osNameMatch.contains("mac os") || osNameMatch.contains("macos") || osNameMatch.contains("darwin")) {
            retOsName = ApplicationConstants.OS_MAC_OS;
        } else {
        }
        return retOsName;
    }
    
    public boolean checkSecureLocationFile(final String filePath)
    {
        Path path = new File(filePath).toPath();
        
        try {
            if (!isInSecureDir(path)) {
                logger.info("File not in secure directory");
                return false;
            }
            if(!Files.isDirectory(path))
            {
                BasicFileAttributes attr = Files.readAttributes(
                        path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);

                if (!attr.isRegularFile()) {
                    logger.info("Not a regular file");
                    return false;
                }
            }
        }
        catch (IOException x) {
            logger.info("IOException Occurred in checkSecureLocationFile.");
            return false;
        }
        return true;
    }
    
    
}
