package carservicecrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "appearance_settings")
public class AppearanceSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titleColor;

    private String tdColor;

    private String tdHoverColor;

    private String fontSelect;

    @NotNull
    private String siteName;

    private String textColor;

    private String image;

    public AppearanceSettings() {
    }

}