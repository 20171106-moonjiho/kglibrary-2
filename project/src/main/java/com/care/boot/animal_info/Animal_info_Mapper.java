package com.care.boot.animal_info;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.care.boot.animal_find.Animal_find_DTO;

@Mapper
public interface Animal_info_Mapper {

	ArrayList<Animal_info_DTO> animal_info(int begin, int end, String search_select, String search);

	int totalCount(String search_select, String search);

	void animal_info_writeProc(Animal_info_DTO board);

	Animal_info_DTO animal_info_Content(int n);

	void ContentDeleteProc3(int n);

	void incrementHits(int n);

	int animal_info_ModifyProc(Animal_info_DTO board);

	void askReplyWrite2(Animal_info_comment_DTO adto);

	Animal_info_comment_DTO askReplyCunfirm2(int n, int pn);

	void askReplyBoardDeleteProc2(int n, int pn);

	List<Animal_info_comment_DTO> askReplyView2(int n);
}
