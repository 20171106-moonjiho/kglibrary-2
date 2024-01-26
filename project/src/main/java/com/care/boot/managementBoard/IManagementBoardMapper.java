package com.care.boot.managementBoard;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IManagementBoardMapper {

	int totalCount(String select, String search);

	List<ManagementBoardDTO> managementForm(int begin, int end, String select, String search);

	void managementWriteProc(ManagementBoardDTO board);

	ManagementBoardDTO managementBoardContent(int n);

	void managementBoardDeleteProc(int n);

	void managementBoardModify(ManagementBoardDTO board);
	
	
}
