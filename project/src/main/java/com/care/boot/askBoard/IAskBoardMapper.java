package com.care.boot.askBoard;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAskBoardMapper {

	int totalCount(String select, String search);

	List<AskBoardDTO> askForm(int begin, int end, String select, String search, String sessionId);

	void askWriteProc(AskBoardDTO board);

	AskBoardDTO askBoardContent(int n);

	void askBoardDeleteProc(int n);

	void askBoardModify(AskBoardDTO board);

	int askReCount(int countNum, String recount);

	List<AskBoardDTO> findDeleteNo(String id);



	
	
}
