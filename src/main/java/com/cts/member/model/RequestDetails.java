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
@Table(name = "REQUEST_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestDetails {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private int SerialNo;
	private String requesterId;
	private String fileName;
	
}
