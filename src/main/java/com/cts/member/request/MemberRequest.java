package com.cts.member.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {

	String requesterId;
	String fileName;
	/* List<MemberDetail> memberList; */
}
