package carservicecrm.services;

import carservicecrm.models.*;
import carservicecrm.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private DetailRepository detailRepository;

    @InjectMocks
    private OfferService offerService;

    private Offer offer;
    private Purchase purchase;
    private Tool tool;
    private Detail detail;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        offer = new Offer();
        offer.setId(1L);
        purchase = new Purchase();
        purchase.setId(1L);
        tool = new Tool();
        tool.setId(1L);
        detail = new Detail();
        detail.setId(1L);
    }

    @Test
    void testListOffers_WithName() {
        String name = "Test Offer";
        when(offerRepository.findAllByName(name)).thenReturn(Collections.emptyList());

        List<Offer> result = offerService.listOffers(name);

        assertNotNull(result);
        verify(offerRepository, times(1)).findAllByName(name);
    }

    @Test
    void testListOffers_WithoutName() {
        when(offerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Offer> result = offerService.listOffers(null);

        assertNotNull(result);
        verify(offerRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByPrincipal_NullPrincipal() {
        Principal principal = null;

        User result = offerService.getUserByPrincipal(principal);

        assertNotNull(result);
    }

    @Test
    void testGetUserByPrincipal_ValidPrincipal() {
        Principal principal = () -> "test@example.com";
        User user = new User();
        when(userRepository.findByEmail(principal.getName())).thenReturn(user);

        User result = offerService.getUserByPrincipal(principal);

        assertEquals(user, result);
    }

    @Test
    void testGetOfferById_Exists() {
        Long id = 1L;
        Offer offer = new Offer();
        when(offerRepository.findById(id)).thenReturn(Optional.of(offer));

        Offer result = offerService.getOfferById(id);

        assertEquals(offer, result);
    }

    @Test
    void testGetOfferById_NotExists() {
        Long id = 1L;
        when(offerRepository.findById(id)).thenReturn(Optional.empty());

        Offer result = offerService.getOfferById(id);

        assertNull(result);
    }

    @Test
    void testSaveProduct_WithFiles() throws IOException {
        Principal principal = () -> "test@example.com";
        Offer offer = new Offer();
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        MultipartFile file3 = mock(MultipartFile.class);

        when(file1.getSize()).thenReturn(1L);
        when(file2.getSize()).thenReturn(0L);
        when(file3.getSize()).thenReturn(0L);

        when(file1.getName()).thenReturn("file1");
        when(file1.getOriginalFilename()).thenReturn("file1.jpg");
        when(file1.getContentType()).thenReturn("image/jpeg");
        when(file1.getBytes()).thenReturn(new byte[0]);

        when(offerRepository.save(offer)).thenReturn(offer);

        offerService.saveProduct(principal, offer, file1, file2, file3);

        assertFalse(offer.getImages().isEmpty());
        verify(offerRepository, times(2)).save(offer);
    }

    @Test
    void testDeleteOffer_Exists() {
        Long id = 1L;
        Offer offer = new Offer();
        when(offerRepository.findById(id)).thenReturn(Optional.of(offer));

        offerService.deleteOffer(id);

        verify(offerRepository, times(1)).delete(offer);
    }

    @Test
    void testDeleteOffer_NotExists() {
        Long id = 1L;
        when(offerRepository.findById(id)).thenReturn(Optional.empty());

        offerService.deleteOffer(id);

        verify(offerRepository, times(0)).delete(any());
    }

    @Test
    void testAddReviewToOffer() {
        Long userId = 1L;
        Review review = new Review();
        Offer offer = new Offer();

        when(offerRepository.findById(userId)).thenReturn(Optional.of(offer));

        offerService.addReviewToOffer(userId, review);

        verify(offerRepository, times(1)).save(offer);
    }

    @Test
    void testRemoveReviewFromOffer() {
        Long userId = 1L;
        Review review = new Review();
        Offer offer = new Offer();

        when(offerRepository.findById(userId)).thenReturn(Optional.of(offer));

        offerService.removeReviewFromOffer(userId, review);

        verify(offerRepository, times(1)).save(offer);
        verify(reviewRepository, times(1)).deleteReviewById(review.getId());
    }

    @Test
    void testRemoveReviewFromOfferThrows() {
        Long userId = 1L;
        Review review = new Review();
        Offer offer = new Offer();
        doThrow(new Exception());
        offerService.removeReviewFromOffer(userId, review);
    }

    @Test
    void testAddPurchaseToOffer() {
        Long userId = 1L;
        Purchase purchase = new Purchase();
        Offer offer = new Offer();

        when(offerRepository.findById(userId)).thenReturn(Optional.of(offer));

        offerService.addPurchaseToOffer(userId, purchase);

        verify(offerRepository, times(1)).save(offer);
    }

    @Test
    void removePurchaseFromOffer_OfferExists() {
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        offerService.removePurchaseFromOffer(1L, purchase);
        verify(offerRepository).save(offer);
        verify(purchaseRepository).deletePurchaseById(purchase.getId());
    }

    @Test
    void removePurchaseFromOffer_OfferNotFound() {
        when(offerRepository.findById(1L)).thenReturn(Optional.empty());
        offerService.removePurchaseFromOffer(1L, purchase);
        verify(offerRepository, never()).save(any());
        verify(purchaseRepository, never()).deletePurchaseById(anyLong());
    }

    @Test
    void addToolToOffer_OfferExists() {
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        offerService.addToolToOffer(1L, tool);
        verify(offerRepository).save(offer);
    }

    @Test
    void addToolToOffer_OfferNotFound() {
        when(offerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> offerService.addToolToOffer(1L, tool));
    }

    @Test
    void removeToolFromOffer_OfferExists() {
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        offerService.removeToolFromOffer(1L, tool);
        verify(offerRepository).save(offer);
        verify(toolRepository).deleteToolById(tool.getId());
    }

    @Test
    void removeToolFromOffer_OfferNotFound() {
        when(offerRepository.findById(1L)).thenReturn(Optional.empty());
        offerService.removeToolFromOffer(1L, tool);
        verify(offerRepository, never()).save(any());
        verify(toolRepository, never()).deleteToolById(anyLong());
    }

    @Test
    void addDetailToOffer_OfferExists() {
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        offerService.addDetailToOffer(1L, detail);
        verify(offerRepository).save(offer);
    }

    @Test
    void addDetailToOffer_OfferNotFound() {
        when(offerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> offerService.addDetailToOffer(1L, detail));
    }

    @Test
    void removeDetailFromOffer_OfferExists() {
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        offerService.removeDetailFromOffer(1L, detail);
        verify(offerRepository).save(offer);
        verify(detailRepository).deleteDetailById(detail.getId());
    }

    @Test
    void removeDetailFromOffer_OfferNotFound() {
        when(offerRepository.findById(1L)).thenReturn(Optional.empty());
        offerService.removeDetailFromOffer(1L, detail);
        verify(offerRepository, never()).save(any());
        verify(detailRepository, never()).deleteDetailById(anyLong());
    }

    @Test
    void getOfferByName_OfferExists() {
        when(offerRepository.findByName("Test Offer")).thenReturn(offer);

        Offer result = offerService.getOfferByName("Test Offer");

        assertEquals(offer, result);
    }

}