package com.cts.member.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.cts.member.model.MemberDetail;
import com.cts.member.model.RequestDetails;

@Repository
public class MemberDao {

	private static final Logger logger = LoggerFactory.getLogger(MemberDao.class);
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public List<MemberDetail> persistMember(List<MemberDetail> memberDetailList) {
		for(MemberDetail memberDetail : memberDetailList)
			em.persist(memberDetail);
		logger.info("\nMember persisted : "+ memberDetailList.size());
		return memberDetailList;
	}

	@Transactional
	public void persistRequestDetail(RequestDetails rd) {
		em.persist(rd);
		logger.info("\nRequested Details persisted");
	}
	
}
