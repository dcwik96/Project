package pl.iledasz.DTO;

public class AdvertPhotoDTO {

    private Long id;
    private String description;

    public AdvertPhotoDTO() {
    }

    public AdvertPhotoDTO(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
