package com.company.postsproject;

public class UserData {

    private int likes;

    private User user;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User{

        private String name;

        private ProfileImage profile_image;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ProfileImage getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(ProfileImage profile_image) {
            this.profile_image = profile_image;
        }

        public class ProfileImage{

            private String medium;


            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }

    }
}
