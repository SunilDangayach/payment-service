package com.paymentservice.service;

import com.paymentservice.entity.ContentCreator;
import com.paymentservice.payloads.request.ContentCreatorRequest;
import com.paymentservice.payloads.response.GenericServiceResponse;
import com.paymentservice.repository.ContentCreatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContentCreatorService {

    public final ContentCreatorRepository contentCreatorRepository;

    public ContentCreatorService(ContentCreatorRepository contentCreatorRepository){
        this.contentCreatorRepository = contentCreatorRepository;
    }

    /****
     *
     * This process is to simulate content creator onboarding.

     * *******/

    public GenericServiceResponse createContentCreator(ContentCreatorRequest contentCreatorRequest){

        ContentCreator existingContentCreator = contentCreatorRepository.findByEmailOrPhone(contentCreatorRequest.getEmail(),contentCreatorRequest.getPhoneNumber());
        log.info("Making a special call on createContentCreator method ");
            if(existingContentCreator != null){

                return new GenericServiceResponse<>()
                .responseCode(HttpStatus.CONFLICT)
                .responseMessage("This content creator already exist")
                .responseBody(null);
            }
            ContentCreator contentCreator = new ContentCreator();
            contentCreator.setFirstName(contentCreatorRequest.getFirstName());
            contentCreator.setLastName(contentCreatorRequest.getLastName());
            contentCreator.setEmail(contentCreatorRequest.getEmail());
            contentCreator.setPhoneNumber(contentCreatorRequest.getPhoneNumber());
            contentCreator.setIsActive(true);
            contentCreatorRepository.save(contentCreator);
            return new GenericServiceResponse()
                    .responseCode(HttpStatus.CREATED)
                    .responseMessage("Successfully created content creator")
                    .responseBody(contentCreator);

    }

    public GenericServiceResponse fetchAllContentCreators(){
        List<ContentCreator> contentCreators = contentCreatorRepository.findAllActiveContentCreators();
        log.info("Making a special call on fetchAllContentCreators method ");
        return new GenericServiceResponse()
                .responseCode(HttpStatus.OK)
                .responseMessage("All content creators successfully retrieved")
                .responseBody(contentCreators);
    }

    public GenericServiceResponse fetchAllContentCreatorById(Long Id){
        Optional<ContentCreator> contentCreator = contentCreatorRepository.findActiveById(Id);
            return new GenericServiceResponse()
                    .responseCode(!contentCreator.isPresent() ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                    .responseMessage(!contentCreator.isPresent()  ? "Content creator with id " + Id
                            + " could not be found": "Content creator was successfully retrieved")
                    .responseBody(!contentCreator.isPresent() ? null : contentCreator.get());

    }

    public GenericServiceResponse updateContentCreator(Long Id,ContentCreatorRequest contentCreatorRequest){
        Optional<ContentCreator> verifyIfExist = contentCreatorRepository.findActiveById(Id);
        if(!verifyIfExist.isPresent()){
            return new GenericServiceResponse()
                    .responseCode(HttpStatus.NOT_FOUND)
                    .responseMessage("The content creator with Id " + Id + " could not be found")
                    .responseBody(null);
        }
        verifyIfExist.get().setEmail(contentCreatorRequest.getEmail() == null ? verifyIfExist.get().getEmail() : contentCreatorRequest.getEmail());
        verifyIfExist.get().setPhoneNumber(contentCreatorRequest.getPhoneNumber() == null ? verifyIfExist.get().getPhoneNumber() : contentCreatorRequest.getPhoneNumber());
        verifyIfExist.get().setFirstName(contentCreatorRequest.getFirstName() == null ? verifyIfExist.get().getFirstName() : contentCreatorRequest.getFirstName());
        verifyIfExist.get().setLastName(contentCreatorRequest.getLastName() == null ? verifyIfExist.get().getLastName() : contentCreatorRequest.getLastName());
        ContentCreator newlyUpdated = contentCreatorRepository.save(verifyIfExist.get());
        return new GenericServiceResponse()
                .responseCode(HttpStatus.OK)
                .responseMessage("Content creator with Id " + Id + " Has successfully been updated")
                .responseBody(newlyUpdated);
    }


    public GenericServiceResponse disableContentCreator(Long Id){
        Optional<ContentCreator> verifyIfExist = contentCreatorRepository.findById(Id);
        if(!verifyIfExist.isPresent()){
            return new GenericServiceResponse()
                    .responseCode(HttpStatus.NOT_FOUND)
                    .responseMessage("The content creator with Id " + Id + " could not be found")
                    .responseBody(null);
        }
        verifyIfExist.get().setIsActive(false);
        ContentCreator newlyUpdated = contentCreatorRepository.save(verifyIfExist.get());
        return new GenericServiceResponse()
                .responseCode(HttpStatus.OK)
                .responseMessage("Content creator with Id " + Id + " Has successfully been deactivated")
                .responseBody(null);
    }


}
