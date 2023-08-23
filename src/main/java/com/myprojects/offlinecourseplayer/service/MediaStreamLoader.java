package com.myprojects.offlinecourseplayer.service;

/*
 * @author: Nagendra
 * version: 1.0.0
 * @created: 21/08/2023 - 14:43
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

public interface MediaStreamLoader {

    ResponseEntity<StreamingResponseBody>
        loadPartialMediaFile(String localMediaFilePath,
                             String rangeValues) throws IOException;

}
