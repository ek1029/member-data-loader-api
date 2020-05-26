package com.cts.member.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "PLANS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Plans {

	@Id
	@GeneratedValue
	int no;
	String planId;
	String planName;
	String status;
	
	
}
