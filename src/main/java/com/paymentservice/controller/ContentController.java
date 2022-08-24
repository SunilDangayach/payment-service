package com.paymentservice.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymentservice.payloads.request.ContentsRequest;
import com.paymentservice.payloads.response.CommonResponse;
import com.paymentservice.payloads.response.GenericServiceResponse;
import com.paymentservice.service.ContentService;

@RestController
@RequestMapping("/v1/api")
public class ContentController {

    public final ContentService contentService;


    public ContentController(ContentService contentService){
        this.contentService = contentService;
    }


    @PostMapping("/CreateContent/{creatorId}")
    public ResponseEntity<Object> CreateContentCreator(@PathVariable("creatorId") Long creatorId, @Valid @RequestBody ContentsRequest contentRequest){
        GenericServiceResponse content = contentService.createContent(creatorId,contentRequest);
        return  CommonResponse.generateResponse(content.responseCode(),
                content.responseBody() == null ? false : true,
                content.responseMessage(),
                content.responseBody());
    }

    @GetMapping("/GetAllContents")
    public ResponseEntity<Object> GetContents(){
        GenericServiceResponse creators = contentService.fetchAllActiveContent();
        return  CommonResponse.generateResponse(creators.responseCode(),
                creators.responseBody() == null ? false : true,
                creators.responseMessage(),
                creators.responseBody());
    }

    @GetMapping("/GetContentById/{contentId}")
    public ResponseEntity<Object> GetContentById(@PathVariable("contentId") Long contentId){
        GenericServiceResponse creators = contentService.fetchContentById(contentId);
        return  CommonResponse.generateResponse(creators.responseCode(),
                creators.responseBody() == null ? false : true,
                creators.responseMessage(),
                creators.responseBody());
    }

    @PutMapping("/UpdateContent{creatorId}")
    public ResponseEntity<Object> UpdateContent(@PathVariable("creatorId") Long contentId,@RequestBody ContentsRequest contentRequest){
        GenericServiceResponse content = contentService.updateContent(contentId,contentRequest);
        return  CommonResponse.generateResponse(content.responseCode(),
                content.responseBody() == null ? false : true,
                content.responseMessage(),
                content.responseBody());
    }

    @DeleteMapping("/DisableContent{contentId}")
    public ResponseEntity<Object> DisableContent(@PathVariable("contentId") Long contentId){
        GenericServiceResponse content = contentService.disableContent(contentId);
        return  CommonResponse.generateResponse(content.responseCode(),
                content.responseBody() == null ? false : true,
                content.responseMessage(),
                content.responseBody());
    }


}
