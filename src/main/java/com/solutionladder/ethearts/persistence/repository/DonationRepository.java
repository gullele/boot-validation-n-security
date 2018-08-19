package com.solutionladder.ethearts.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.solutionladder.ethearts.persistence.entity.MonetaryDonation;

/**
 * Repository for donation(money)
 * 
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 * @date 11 - Aug - 2018
 */
@Repository
public interface DonationRepository extends CrudRepository<MonetaryDonation, Long> {

    //select ( (select sum(d.deposit) from deposit) - (select sum(d.donation) from donations);
    //@Query("SELECT SUM(d.deposit) FROM Deposit d WHERE d.member = :member")
    //public Double getDonatableMoney(@Param("member") Member memeber);
}
