package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.service.SelectOfferService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class SelectOfferController {

    @Autowired
    SelectOfferService selectOfferService;

    @RequestMapping(value = "api/advert/select/{offerID}")
    public void chooseOffer(@PathVariable("offerID") Long offerID, Principal principal, HttpServletResponse httpServletResponse){

        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        if (selectOfferService.chooseOneOffer(offerID, principal))
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
