package pl.iledasz.DTO;


public class PhotoDTO {

    private Long id;

    private byte[] image;

    public PhotoDTO(Long id, byte[] image) {
        this.id = id;
        this.image = image;
    }

    public PhotoDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
