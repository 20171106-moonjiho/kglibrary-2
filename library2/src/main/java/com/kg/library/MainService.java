package com.kg.library;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.kg.library.Introduction.BookDTO;

@Service
public class MainService {

	private static final Logger logger = LoggerFactory.getLogger(MainService.class);

	public void hit_book(Model model) {

		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

			 
			 ResponseEntity<ArrayList<BookDTO>> responseEntity = restTemplate.exchange(
		                "http://www.bowfun.link/book/hit_book", HttpMethod.GET, null,
		                new ParameterizedTypeReference<ArrayList<BookDTO>>() {
		                });

			ArrayList<BookDTO> hitbooks = responseEntity.getBody();
			System.out.println("hit_book 요청 보냄");
			if (hitbooks != null) {
				for (BookDTO b : hitbooks) {
					if (b.getImage() == null || b.getImage().trim().isEmpty()) {
						b.setImage("20240109150111-40641325628.20230718121618.jpg");
						continue;
					}
				}
			}
			// 모델에 데이터 추가
			model.addAttribute("hitbooks", hitbooks);

		} catch (HttpClientErrorException.NotFound notFoundException) {
			// 404 Not Found 에러 처리
			logger.error("Server returned 404 Not Found");
			// 다른 예외에 대한 처리도 추가 가능
		} catch (Exception e) {
			// 기타 예외 처리
			logger.error("An error occurred while fetching data from the server", e);
		}

	}

	public void new_book(Model model) {

		try {
			ResponseEntity<ArrayList<BookDTO>> responseEntity = new RestTemplate().exchange(
					"http://www.bowfun.link/book/new_Book", HttpMethod.GET, null,
					new ParameterizedTypeReference<ArrayList<BookDTO>>() {
					});

			ArrayList<BookDTO> newBooks = responseEntity.getBody();
			System.out.println("newBooks 요청 보냄");
			if (newBooks != null) {
				for (BookDTO b : newBooks) {
					System.out.print("\u001B[31m");
					System.out.println("8086newbook Category: " + b.getCategory());
					System.out.println("8086newbook Image: " + b.getImage());
					System.out.println("8086newbook Title Info: " + b.getTitle_info());
					System.out.println("8086newbook Author Info: " + b.getAuthor_info());
					System.out.print("\u001B[0m");
					if (b.getImage() == null || b.getImage().trim().isEmpty()) {
						b.setImage("20240109150111-40641325628.20230718121618.jpg");
						continue;
					}
					if (b.getCategory().equals("API")) // API에서 받아온 이미지 라면
					{
						continue;
					}
				}
			}
			// 모델에 데이터 추가
			model.addAttribute("newBooks", newBooks);

		} catch (HttpClientErrorException.NotFound notFoundException) {
			// 404 Not Found 에러 처리
			logger.error("Server returned 404 Not Found");
			// 다른 예외에 대한 처리도 추가 가능
		} catch (Exception e) {
			// 기타 예외 처리
			logger.error("An error occurred while fetching data from the server", e);
		}

	}

	public String search(String cp, Model model, String search2, String select) {
		
		try {
			ResponseEntity<ArrayList<BookDTO>> responseEntity = new RestTemplate().exchange(
					"http://www.bowfun.link/book/search", HttpMethod.GET, null,
					new ParameterizedTypeReference<ArrayList<BookDTO>>() {
					});

			ArrayList<BookDTO> search = responseEntity.getBody();

			if (search != null) {
				for (BookDTO b : search) {
					System.out.print("\u001B[31m");
					System.out.println("8086newbook Category: " + b.getCategory());
					System.out.println("8086newbook Image: " + b.getImage());
					System.out.println("8086newbook Title Info: " + b.getTitle_info());
					System.out.println("8086newbook Author Info: " + b.getAuthor_info());
					System.out.print("\u001B[0m");
					if (b.getImage() == null || b.getImage().trim().isEmpty()) {
						b.setImage("20240109150111-40641325628.20230718121618.jpg");
						continue;
					}
					if (b.getCategory().equals("API")) // API에서 받아온 이미지 라면
					{
						continue;
					}
				}
			}
			// 모델에 데이터 추가
			model.addAttribute("newBooks", search);

		} catch (HttpClientErrorException.NotFound notFoundException) {
			// 404 Not Found 에러 처리
			logger.error("Server returned 404 Not Found");
			// 다른 예외에 대한 처리도 추가 가능
		} catch (Exception e) {
			// 기타 예외 처리
			logger.error("An error occurred while fetching data from the server", e);
		}
		return null;
	}
}
