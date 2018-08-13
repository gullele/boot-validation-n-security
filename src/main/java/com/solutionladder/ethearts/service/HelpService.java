package com.solutionladder.ethearts.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solutionladder.ethearts.persistence.entity.Contribution;
import com.solutionladder.ethearts.persistence.entity.Help;
import com.solutionladder.ethearts.persistence.entity.HelpType;
import com.solutionladder.ethearts.persistence.repository.ContributionRepository;
import com.solutionladder.ethearts.persistence.repository.GenericRepository;
import com.solutionladder.ethearts.persistence.repository.HelpRepository;

/**
 * Service class for Help
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 *
 */
@Service
public class HelpService {

    @Autowired
    public HelpRepository helpRepository;
    
    @Autowired
    public GenericRepository<HelpType> helpTypeRepository;
    
    @Autowired
    public ContributionRepository contributionRepository;
    
    public Help save(Help help) {
        this.helpRepository.save(help);
        return help;
    }

    public Iterable<Help> getAll() {
        return this.helpRepository.findAll();
    }
    
    public HelpType save(HelpType helpType) {
        this.helpTypeRepository.save(helpType);
        return helpType;
    }
    
    public Contribution saveContribution(Contribution contribution) {
        this.contributionRepository.save(contribution);
        return contribution;
    }

    public Help get(Long id) {
        Optional<Help> Help = this.helpRepository.findById(id);
        if (Help.isPresent()) return Help.get(); 
        return null;
    }
}
