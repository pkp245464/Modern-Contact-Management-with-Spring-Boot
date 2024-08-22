package com.scm.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile,MultipartFile> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; //2MB File Size MAX

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        
        //check file is empty or not
        if(file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();
            return false;
        }
        // System.out.println("File Size: " + file.getSize());
        logger.info("File Size of uploaded image" + file.getSize());
        
        //check file size is exceed
        if(file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 2MB").addConstraintViolation();
            return false;
        }

        //check file resolution
        // try {
        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        //     if(bufferedImage.getHeight()) 

        // } 
        // catch (IOException e) {
            
        //     e.printStackTrace();
        // }
        return true;
    }

}
