package carservicecrm.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "offer")
@Getter
@Setter
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, updatable = false)
    private String name;
    private String description;
    private Integer price;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "offer")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;


    @ManyToMany(mappedBy = "offers")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Review> reviews = new HashSet<>();

    @ManyToMany(mappedBy = "offers")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Purchase> purchases = new HashSet<>();

    @ManyToMany(mappedBy = "offers")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Tool> tools = new HashSet<>();

    @ManyToMany(mappedBy = "offers")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Detail> details = new HashSet<>();

    public void addReview(Review review) {
        reviews.add(review);
        review.getOffers().add(this);
    }

    public void removeReview(Review review) {
        try {
            reviews.remove(review);
            review.getOffers().remove(this);
        } catch (Exception ignored) {

        }

    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        purchase.getOffers().add(this);
    }

    public void removePurchase(Purchase purchase) {
        try {
            purchases.remove(purchase);
            purchase.getOffers().remove(this);
        } catch (Exception ignored) {

        }
    }


    public void addTool(Tool tool) {
        tools.add(tool);
        tool.getOffers().add(this);
    }

    public void removeTool(Tool tool) {
        try {
            tools.remove(tool);
            tool.getOffers().remove(this);
        } catch (Exception ignored) {

        }
    }

    public void addDetail(Detail detail) {
        details.add(detail);
        detail.getOffers().add(this);
    }

    public void removeDetail(Detail detail) {
        try {
            details.remove(detail);
            detail.getOffers().remove(this);
        } catch (Exception ignored) {

        }
    }

    public void addImageToOffer(Image image) {
        image.setOffer(this);
        images.add(image);
    }

}
