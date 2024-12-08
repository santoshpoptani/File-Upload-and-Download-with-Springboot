package com.learnig.upload.service;

import com.learnig.upload.entity.Attachment;
import com.learnig.upload.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentService {

    private AttachmentRepository attachmentRepository;
    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }


    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
        if(fileName.contains("..")){
            throw new Exception("Invalid file name " + fileName);
        }
        Attachment attachment = new Attachment(fileName , file.getContentType() , file.getBytes());
        return attachmentRepository.save(attachment);
        }
        catch (Exception e){
            throw  new Exception("Could not save file " + fileName);
        }
    }

    public Attachment getAttchment(String fileId) throws Exception {
        return attachmentRepository.findById(fileId)
                .orElseThrow(()->new Exception("File not found "+ fileId));
    }
}
