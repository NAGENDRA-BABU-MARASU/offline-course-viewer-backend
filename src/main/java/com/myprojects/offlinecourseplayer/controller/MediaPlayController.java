package com.myprojects.offlinecourseplayer.controller;

/*
 * @author: Nagendra
 * version: 1.0.0
 * @created: 21/08/2023 - 14:37
 * credits: https://www.codeproject.com/Articles/5341970/Streaming-Media-Files-in-Spring-Boot-Web-Applicati
 */

import com.myprojects.offlinecourseplayer.service.MediaStreamLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;

@CrossOrigin(maxAge = 3600)
@RestController
public class MediaPlayController {
    private final MediaStreamLoader mediaLoaderService;

    public MediaPlayController(MediaStreamLoader mediaLoaderService){
        this.mediaLoaderService = mediaLoaderService;
    }

    @GetMapping(value = "/play/media/")
    public ResponseEntity<StreamingResponseBody> playMedia(
            @RequestParam("vid_name") String video_name,
            @RequestHeader(value = "Range", required = false) String rangeHeader
    ) {
        try{
            return mediaLoaderService.loadPartialMediaFile(video_name, rangeHeader);
        }
        catch(FileNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
