package com.care.boot.utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

public class FileUploadUtils {
	

	/**
	 * 로컬 프로젝트에 파일 저장
	 * webapp/resources/ 하위에 저장된다
	 * @param file
	 * @param folderName 존재하는 폴더명
	 * @return
	 * @throws IOException
	 */
	public static String fileUpload(MultipartFile file, ServletContext servletContext, String folderName) throws IOException {
		
		System.out.println(" >>>>>>>>>>>>>>>>> 파일명 : " + file.getOriginalFilename() + " / 파일 사이즈 : " + file.getSize());
		
		String webappRoot = servletContext.getRealPath("/");
		String relativePathFolder = "resources" + File.separator + folderName + File.separator;

		String newFileName = generateRandomFileName();
		String storeAs = relativePathFolder + newFileName;
		
		String fileName = webappRoot + relativePathFolder + newFileName;
		
		FileCopyUtils.copy(file.getBytes(), new File(fileName));
		
		return storeAs;
		
	}
	
	/**
	 * 랜덤 파일명 생성
	 * @return 파일명
	 */
    public static String generateRandomFileName() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomFileName = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            randomFileName.append(characters.charAt(index));
        }

        System.out.println("Random Filename: " + randomFileName);
        return randomFileName.toString();
    }
}
