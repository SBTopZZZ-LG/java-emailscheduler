package Models;

import java.awt.*;

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
    private final String emailAddress;
    private final String imageLocation;
    private final String position;
    private final String bio;
    private final Color color;
    private final Socials socials;

    public User(String fullName, String emailAddress, String imageLocation, String position, String bio, Socials socials) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.imageLocation = imageLocation;
        this.position = position;
        this.bio = bio;
        this.color = Color.BLACK;
        this.socials = socials;
    }

    public User(String fullName, String emailAddress, String imageLocation, String position, String bio, Color color, Socials socials) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.imageLocation = imageLocation;
        this.position = position;
        this.bio = bio;
        this.color = color;
        this.socials = socials;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public String getPosition() {
        return position;
    }

    public String getBio() {
        return bio;
    }

    public Color getColor() {
        return color;
    }

    public Socials getSocials() {
        return socials;
    }
}
