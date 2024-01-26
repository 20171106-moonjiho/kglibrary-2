package com.care.boot.member;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberMapper {

	MemberDTO login(String id); //db와 이름을 맞춰 놨기에 resultmap 따로 사용 안함.

	int registProc(MemberDTO member);

	int updateProc(MemberDTO member);

	int deleteProc(String id);

	ArrayList<MemberDTO> memberInfo(int begin, int end, String select, String search);

	int totalCount(String select, String search);

}