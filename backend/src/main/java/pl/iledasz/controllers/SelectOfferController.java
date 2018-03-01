package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.entities.ChosenOffer;
import pl.iledasz.repository.ChosenOfferRepository;
import pl.iledasz.service.ChosenOfferService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
public class SelectOfferController {

    @Autowired
    ChosenOfferService chosenOfferService;

    @Autowired
    ChosenOfferRepository chosenOfferRepository;

    @RequestMapping(value = "api/advert/select/{offerID}")
    public void chooseOffer(@PathVariable("offerID") Long offerID, Principal principal, HttpServletResponse httpServletResponse){

        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        if (chosenOfferService.chooseOneOffer(offerID, principal))
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }


    @RequestMapping(value = "api/requests")
    public List<ChosenOffer> getAwaitingRequests(Principal principal){

        return chosenOfferRepository.findAllByOffer_AppUser_LoginAndApprovedIsNullAndExpiredDateAfter(principal.getName(), OffsetDateTime.now());
    }
}
