package com.care.boot.find;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FindMapper {

	List<FindDTO> findForm(@Param("begin")int begin, @Param("end")int end);

	int totalCount();

	void findWriteProc(FindDTO find);

	void incrementHits(int n);

	FindDTO findContent(int n);

	void deleteFind(int n);

	int findEditProc(FindDTO board);

	int insertComment(CommentDTO commentDTO);

	List<CommentDTO> selectComments(int n);

	int deleteComment(CommentDTO DTO);



}
