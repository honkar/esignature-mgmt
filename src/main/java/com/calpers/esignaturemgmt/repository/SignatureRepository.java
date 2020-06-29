package com.calpers.esignaturemgmt.repository;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calpers.esignaturemgmt.model.Signature;

@Repository
public interface SignatureRepository extends JpaRepository<Signature,Long>{
	// without version history
	Signature findByUserId(long userId);
	
	//with versioning 
	Signature findByUserIdAndStatus(long userId, int status);
	
	@Modifying
	@Transactional
	@Query("Update Signature g set g.status = ?1 where g.userId = ?2")
	int setSignatureStatus(int status, long userId);

}
