package com.care.boot.animal_protect;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface Animal_protect_Mapper {

	ArrayList<Animal_protect_DTO> animal_protect(int begin, int end, String animal_group, String search, String area);

	int totalCount(String animal_group, String search, String area);

	void animal_protect_writeProc(Animal_protect_DTO board);

	void ContentDeleteProc2(int n);

	Animal_protect_DTO animal_protect_Content(int n);

	int animal_protect_ModifyProc(Animal_protect_DTO board);

	ArrayList<Animal_protect_DTO> animal_protect_main();

}
