package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.iledasz.DTO.ChosenOfferDTO;
import pl.iledasz.service.ChosenOfferService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
public class ChosenOfferController {

    @Autowired
    ChosenOfferService chosenOfferService;


    @RequestMapping(value = "api/advert/select/{offerID}")
    public void chooseOffer(@PathVariable("offerID") Long offerID, Principal principal, HttpServletResponse httpServletResponse){

        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        if (chosenOfferService.chooseOneOffer(offerID, principal))
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }


    @RequestMapping(value = "api/requests")
    public List<ChosenOfferDTO> getAwaitingRequests(Principal principal){

        return chosenOfferService.getAwaitingRequests(principal);
    }

    @RequestMapping(value = "api/requests/{requestId}")
    public void ConfirmRequest(@PathVariable("requestId")Long requestID, @RequestParam(value = "reaction") Boolean reaction, Principal principal, HttpServletResponse httpServletResponse){

        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        if (reaction != null && chosenOfferService.confirmOrReject(requestID, principal, reaction))
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
