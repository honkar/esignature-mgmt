package com.calpers.esignaturemgmt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calpers.esignaturemgmt.model.Signature;

@Repository
public interface SignatureRepository extends JpaRepository<Signature,Long>{

}
