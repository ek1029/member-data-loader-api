package com.cts.member.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "USER_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor


@NamedQueries({
	@NamedQuery(name = "User.getUserByUserName", query = "SELECT u FROM User u WHERE u.userName= :userName")
})

public class User {
	
	@Id
	int id;
	String userName;
	String password;
	String emailId;
	
}
