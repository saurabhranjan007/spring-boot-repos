package controllers;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class FileController {

    // Upload directory where the upload files are
    private String UPLOAD_DIR="C:\\pproject-files\\JAVA\\Udemy\\udemy-sub-projects\\section19\\documents\\uploads";

    // Method to upload the file - REST endpoint for upload
    // Here we need to specify Spring which file will have the entire data using "@RequestParam()"
    // Specifying the url to which this will post map here "/upload"

    @PostMapping("/upload")
    public boolean upload(@RequestParam("file") MultipartFile file) throws IOException {

        // When we specify this all the files will be put into this "new File(_DESTINATION_DIR_)"
        // "file.getOriginalFilename()" - will return the exact file name with which we've uploaded

        file.transferTo(new File(UPLOAD_DIR+file.getOriginalFilename()));
        return true;  // If the upload was successful then it will return true
    }

    // REST endpoint for downloading a file
    // "ResponseEntity" - define the type of data we want to send back
    // Here to inject the "fileName", we use "@PathVariable()"

    @GetMapping("/download/{fileName}")  // mapping to download specific file ("fileName")
    public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) throws IOException {

        // "fileName" will give the idea about the file a user is requesting.
        // Now we can read all files in "documents" directory and respond back with same.
        byte[] fileData = Files.readAllBytes(new File(UPLOAD_DIR+fileName).toPath());

        // the type of data to be returned (here ".jpeg")
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // returning the data with "HTTP" header and status
        return new ResponseEntity<byte[]>(fileData,headers,HttpStatus.OK);
    }
}
