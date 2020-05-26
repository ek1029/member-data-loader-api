package com.cts.member.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "MEMBER_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@NamedQueries({
	
		@NamedQuery(name = "MemberDetail.fetchByPlanId", query = "SELECT m FROM MemberDetail m WHERE m.memberId= :memberId") 

})
public class MemberDetail {

	@Id
	@Column(updatable = false , nullable = false)
	int memberId;
	String name;
	@Temporal(TemporalType.DATE)
	Date dob;
	int planId;
	int dependentsNo;
	@Temporal(TemporalType.DATE)
	Date planStartDate;
	@Temporal(TemporalType.DATE)
	Date planEndDate;
	String planName;
	String planStatus;

}
