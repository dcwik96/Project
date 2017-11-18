package pl.iledasz.DTO;


public class PhotoDTO {

    private byte[] image;

    public PhotoDTO(Long id, byte[] image) {
        this.image = image;
    }

    public PhotoDTO() {

    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
