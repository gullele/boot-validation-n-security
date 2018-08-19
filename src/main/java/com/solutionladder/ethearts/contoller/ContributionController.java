package com.solutionladder.ethearts.contoller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutionladder.ethearts.model.errorhandler.InvalidArgumentException;
import com.solutionladder.ethearts.model.response.GenericResponse;
import com.solutionladder.ethearts.persistence.entity.Deposit;
import com.solutionladder.ethearts.persistence.entity.Member;
import com.solutionladder.ethearts.persistence.entity.MonetaryDonation;
import com.solutionladder.ethearts.security.CustomUser;
import com.solutionladder.ethearts.service.DonationService;

/**
 * Controller facing the contribution related APIs - this mainly focuses on
 * monetary contribution
 * 
 * @author Kaleb Woldearegay <kaleb@solutionladder.com>
 * @date 11 - Aug - 2018
 */
@RestController
@RequestMapping(path = "/api/contribution")
@CrossOrigin(origins = "*")
public class ContributionController extends BaseController {

    @Autowired
    private DonationService donationService;

    @PostMapping(path = { "/", "" })
    public ResponseEntity<GenericResponse> addDonation(@Valid @RequestBody MonetaryDonation donation,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.checkValidationErrors(bindingResult);
        }

        /*
         * Validate more regarding the amount the member is donating. member
         * should have enough amount of money to donate.
         * 
         * @todo - add this to the validation??
         */
        // get available amount of money
        // check if the money is above or equal to the donation amount
        // save the donation
        boolean canDonate = this.donationService.canDonate(donation.getMember(), donation.getContribution());

        if (canDonate) {
            this.donationService.saveDonation(donation);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK); // header sent as
                                                              // OK for
                                                              // insufficient
                                                              // fund
        }

        return new ResponseEntity<>(null, HttpStatus.CREATED); // header sent as
                                                               // created
    }

    /**
     * Allow members to deposit money.
     * 
     * @return
     */
    @PostMapping(path = { "/deposit", "/deposit/" })
    public ResponseEntity<GenericResponse> deposit(@Valid @RequestBody Deposit deposit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.checkValidationErrors(bindingResult);
        }

        GenericResponse response = this.getInitalGenericResponse();

        try {
            deposit.setMember(this.getCurrentMember());
            deposit = this.donationService.saveDeposit(deposit);
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new InvalidArgumentException("another error here", null, Arrays.asList());
        }
    }
}
