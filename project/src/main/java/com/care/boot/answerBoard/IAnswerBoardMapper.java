package com.care.boot.answerBoard;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAnswerBoardMapper {

	int totalCount(String select, String search);

	List<AnswerBoardDTO> answerForm(int begin, int end, String select, String search);

	void answerWriteProc(AnswerBoardDTO board);

	AnswerBoardDTO answerBoardContent(int n);

	void answerBoardDeleteProc(int n);

	void answerBoardModify(AnswerBoardDTO board);
	
	
}
