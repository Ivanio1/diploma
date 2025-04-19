package carservicecrm.controllers;

import carservicecrm.models.Offer;
import carservicecrm.models.User;
import carservicecrm.services.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OfferControllerTest {

    @InjectMocks
    private OfferController offerController;

    @Mock
    private OfferService offerService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void offers_ShouldAddAttributesAndReturnOffersView() {
        String searchWord = "test";
        when(offerService.listOffers(searchWord)).thenReturn(List.of());
        when(offerService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = offerController.offers(searchWord, principal, model);

        verify(model).addAttribute("offers", offerService.listOffers(searchWord));
        verify(model).addAttribute("user", offerService.getUserByPrincipal(principal));
        verify(model).addAttribute("searchWord", searchWord);
        assertEquals("offers", result);
    }

    @Test
    void offerInfo_ShouldAddAttributesAndReturnOfferInfoView() {
        Long offerId = 1L;
        Offer offer = new Offer();
        when(offerService.getOfferById(offerId)).thenReturn(offer);
        when(offerService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = offerController.offerInfo(offerId, model, principal);

        verify(model).addAttribute("user", offerService.getUserByPrincipal(principal));
        verify(model).addAttribute("offer", offer);
        verify(model).addAttribute("images", offer.getImages());
        assertEquals("offer-info", result);
    }

    @Test
    void offerAdd_ShouldAddUserAndReturnAddOfferView() {
        when(offerService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = offerController.offeradd(model, principal);

        verify(model).addAttribute("user", offerService.getUserByPrincipal(principal));
        assertEquals("add-offer", result);
    }

    @Test
    void offerDeleteForm_ShouldAddAttributesAndReturnDeleteOfferView() {
        String searchWord = "test";
        when(offerService.listOffers(searchWord)).thenReturn(List.of());
        when(offerService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = offerController.offerdeleteform(searchWord, model, principal);

        verify(model).addAttribute("user", offerService.getUserByPrincipal(principal));
        verify(model).addAttribute("offers", offerService.listOffers(searchWord));
        assertEquals("delete-offer", result);
    }

    @Test
    void createProduct_ShouldCallServiceAndRedirect() throws IOException {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        MultipartFile file3 = mock(MultipartFile.class);
        Offer offer = new Offer();

        String result = offerController.createProduct(file1, file2, file3, offer, principal);

        verify(offerService).saveProduct(principal, offer, file1, file2, file3);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void deleteProduct_ShouldCallServiceAndRedirect() {
        Long offerId = 1L;

        String result = offerController.deleteProduct(offerId, principal);

        verify(offerService).deleteOffer(offerId);
        assertEquals("redirect:/admin", result);
    }
}