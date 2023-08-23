package com.myprojects.offlinecourseplayer.controller;

/*
 * @author: Nagendra
 * version: 1.0.0
 * @created: 22/08/2023 - 06:42
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;


@CrossOrigin(maxAge = 3600)
@RestController
public class FolderLoaderController {
    final private String MAIN_FOLDER = "C:/Users/nagen/Documents/OFFLINE-COURSE-PLAYER-COURSES/";

    @GetMapping("/media/getCourses/")
    public ResponseEntity<Map<String, Object>> getCourses(){
        Map<String, Object> courseNames = new HashMap<>();
        File dir = new File(MAIN_FOLDER);
        File[] content = dir.listFiles();
        List<String> folderNames = new LinkedList<>();
        List<String> encodedFolderNames = new LinkedList<>();
        for(File f: content){
            if(f.isDirectory()){
                folderNames.add(f.getName());
                encodedFolderNames.add( URLEncoder.encode(f.getName()) );
            }
        }
        courseNames.put("courses", folderNames);
        courseNames.put("encodedCoursesNames", encodedFolderNames);
        return new ResponseEntity<>(courseNames, HttpStatus.OK);
    }

    @GetMapping("/media/getFiles/{folderName}")
    public ResponseEntity<Map<String, Object>> getFolders(@PathVariable("folderName") String folderName) {
//        folderName = "C:/Users/nagen/Downloads/[FreeCourseSite.com] Udemy - The Ultimate DevOps Bootcamp - 2023/";
        folderName = MAIN_FOLDER + URLDecoder.decode(folderName);
        System.out.println(folderName);
        File dir = new File(folderName);
        Map<String, Object> response = listDirectory(dir);
        return new  ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    public Map<String, Object> listDirectory(File dir){
        File[] content = dir.listFiles();
        List<String> encodedUrls = new LinkedList<>();
        List<String> files = new LinkedList<>();
        List<Map<String, Object>> folders = new LinkedList<>();
        for(File f: content){
            if(f.isDirectory()){
                encodedUrls.add( f.toPath().toString());
                Map<String, Object> sublist = listDirectory( f );
                folders.add(sublist);
            } else {
                files.add( f.toString() );
                encodedUrls.add( URLEncoder.encode(f.toString()) );
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("folders", folders);
        result.put("encodedUrls", encodedUrls);
        result.put("files", files);
        return result;
    }
}
