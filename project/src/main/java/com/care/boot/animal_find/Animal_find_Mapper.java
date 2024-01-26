package com.care.boot.animal_find;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
 
@Mapper
public interface Animal_find_Mapper {

	ArrayList<Animal_find_DTO> animal_find(@Param("begin")int begin, @Param("end")int end,@Param("animal_group")String animal_group,@Param("search")String search,@Param("area")String area);

	int totalCount(@Param("animal_group")String animal_group,@Param("search")String search,@Param("area")String area);

	void animal_find_writeProc(Animal_find_DTO board);

	Animal_find_DTO animal_find_image();

	Animal_find_DTO animal_find_Content(int n);

	void ContentDeleteProc(int n);

	int animal_find_ModifyProc(Animal_find_DTO board);

	ArrayList<Animal_find_DTO> animal_find_main();


	
}
