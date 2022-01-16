package Models;

public class User {
    public static class Socials {
        private final String linkedIn;
        private final String instagram;

        public Socials(String linkedIn, String instagram) {
            this.linkedIn = linkedIn;
            this.instagram = instagram;
        }

        public String getLinkedIn() {
            return linkedIn;
        }

        public String getInstagram() {
            return instagram;
        }
    }

    private final String fullName;
    private final String usn;
    private final String imageLocation;
    private final Socials socials;

    public User(String fullName, String usn, String imageLocation, Socials socials) {
        this.fullName = fullName;
        this.usn = usn;
        this.imageLocation = imageLocation;
        this.socials = socials;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsn() {
        return usn;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public Socials getSocials() {
        return socials;
    }
}
