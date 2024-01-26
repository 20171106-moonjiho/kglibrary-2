package com.care.boot.askBoard;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAskReplyMapper {

	List<AskReplyDTO> askReplyView(int n);

	void askReplyWrite(AskReplyDTO adto);

	void askReplyBoardDeleteProc(int n, int pn);

	AskReplyDTO askReplyCunfirm(int n, int pn);

	void findDeleteRplyBoard(int no);

	
	
	
}
