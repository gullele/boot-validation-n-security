package com.solutionladder.ethearts.persistence.entity;

import javax.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;
/**
 * Model representing role class
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 *
 */
@Entity
public class Role extends Lookup implements GrantedAuthority{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String getAuthority() {
        return this.getName();
    }
}
