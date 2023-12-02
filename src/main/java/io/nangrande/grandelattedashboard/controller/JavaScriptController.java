package io.nangrande.grandelattedashboard.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class JavaScriptController {

    @GetMapping("/js/jquery.js")
    @ResponseBody
    public ResponseEntity<Resource> serveJquery() throws IOException {
        // Load the JavaScript file from the classpath
        ClassPathResource jqueryResource = new ClassPathResource("static/js/jquery-3.6.0.min.js");

        if (jqueryResource.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // Set the correct content type
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(jqueryResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
