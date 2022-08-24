package com.paymentservice.service;

import com.paymentservice.entity.ContentCreator;
import com.paymentservice.entity.Contents;
import com.paymentservice.payloads.request.ContentCreatorRequest;
import com.paymentservice.payloads.request.ContentsRequest;
import com.paymentservice.payloads.response.GenericServiceResponse;
import com.paymentservice.repository.ContentCreatorRepository;
import com.paymentservice.repository.ContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContentService {

    public final ContentRepository contentRepository;
    public final ContentCreatorRepository contentCreatorRepository;

    public ContentService(ContentRepository contentRepository,ContentCreatorRepository contentCreatorRepository){
        this.contentRepository = contentRepository;
        this.contentCreatorRepository = contentCreatorRepository;
    }

    public GenericServiceResponse createContent(Long contentCreatorId, ContentsRequest contentsRequest){

        Optional<ContentCreator> existingContentCreator = contentCreatorRepository.findById(contentCreatorId);
        log.info("Making a special call on createContentCreator method ");
        if(!existingContentCreator.isPresent()){

            return new GenericServiceResponse<>()
                    .responseCode(HttpStatus.NOT_FOUND)
                    .responseMessage("This content  with id " + contentCreatorId + " does not exist")
                    .responseBody(null);
        }
        Contents content = new Contents();
        content.setContent(contentsRequest.getContent());
        content.setTitle(contentsRequest.getTitle());
        content.setDescription(contentsRequest.getDescription());
        content.setContentAmount(contentsRequest.getContentAmount());
        content.setIsActive(true);
        content.setContentCreator(existingContentCreator.get());
        contentRepository.save(content);
        return new GenericServiceResponse()
                .responseCode(HttpStatus.CREATED)
                .responseMessage("Successfully created content creator")
                .responseBody(content);

    }

    public GenericServiceResponse fetchAllActiveContent(){
        List<Contents> contents = contentRepository.findAllActiveContent();
        log.info("Making a special call on Contents method ");
        return new GenericServiceResponse()
                .responseCode(HttpStatus.OK)
                .responseMessage("All content successfully retrieved")
                .responseBody(contents);
    }

    public GenericServiceResponse fetchContentById(Long Id){
        Optional<Contents> content = contentRepository.findActiveById(Id);
        return new GenericServiceResponse()
                .responseCode(!content.isPresent() ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .responseMessage(!content.isPresent()  ? "Content  with id " + Id
                        + " could not be found": "Content  was successfully retrieved")
                .responseBody(!content.isPresent() ? null : content.get());

    }

    public GenericServiceResponse updateContent(Long Id,ContentsRequest contentsRequest){
        Optional<Contents> verifyIfExist = contentRepository.findActiveById(Id);
        if(!verifyIfExist.isPresent()){
            return new GenericServiceResponse()
                    .responseCode(HttpStatus.NOT_FOUND)
                    .responseMessage("The content  with Id " + Id + " could not be found")
                    .responseBody(null);
        }
        verifyIfExist.get().setContent(contentsRequest.getContent() == null ? verifyIfExist.get().getContent() : contentsRequest.getContent());
        verifyIfExist.get().setDescription(contentsRequest.getDescription() == null ? verifyIfExist.get().getDescription() : contentsRequest.getDescription());
        verifyIfExist.get().setTitle(contentsRequest.getDescription() == null ? verifyIfExist.get().getDescription() : contentsRequest.getDescription());
        verifyIfExist.get().setContentAmount(contentsRequest.getContentAmount() == 0 ? verifyIfExist.get().getContentAmount() : contentsRequest.getContentAmount());
        Contents newlyUpdated = contentRepository.save(verifyIfExist.get());
        return new GenericServiceResponse()
                .responseCode(HttpStatus.OK)
                .responseMessage("The Content  with Id " + Id + " Has successfully been updated")
                .responseBody(newlyUpdated);
     }

    public GenericServiceResponse disableContent(Long Id){
        Optional<Contents> verifyIfExist = contentRepository.findActiveById(Id);
        if(!verifyIfExist.isPresent()){
            return new GenericServiceResponse()
                    .responseCode(HttpStatus.NOT_FOUND)
                    .responseMessage("The content with Id " + Id + " could not be found")
                    .responseBody(null);
        }
        verifyIfExist.get().setIsActive(false);
        Contents newlyUpdated = contentRepository.save(verifyIfExist.get());
        return new GenericServiceResponse()
                .responseCode(HttpStatus.OK)
                .responseMessage("Content with Id " + Id + " Has successfully been deactivated")
                .responseBody(null);
    }



}
