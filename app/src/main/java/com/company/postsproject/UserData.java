package com.company.postsproject;

public class UserData {

    private int likes;
    private User user;

    public User getUser() {
        return user;
    }

    public int getLikes() {
        return likes;
    }



    public class User{

        private String name;
        private ProfileImage profile_image;

        public String getName() {
            return name;
        }

        public ProfileImage getProfileImage() {
            return profile_image;
        }


        public class ProfileImage{
            private String medium;

            public String getMedium() {
                return medium;
            }

        }
    }
}


