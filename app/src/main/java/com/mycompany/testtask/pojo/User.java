package com.mycompany.testtask.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("company")
    @Expose
    private Company company;

    protected User(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        username = in.readString();
        email = in.readString();
        phone = in.readString();
        website = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }


    public String getPhone() {
        return phone;
    }


    public String getWebsite() {
        return website;
    }


    public Company getCompany() {
        return company;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(website);
    }

    public class Company {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("catchPhrase")
        @Expose
        private String catchPhrase;
        @SerializedName("bs")
        @Expose
        private String bs;

        public String getName() {
            return name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }


        public String getBs() {
            return bs;
        }

    }

    public class Address {

        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("suite")
        @Expose
        private String suite;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("zipcode")
        @Expose
        private String zipcode;
        @SerializedName("geo")
        @Expose
        private Geo geo;

        public String getStreet() {
            return street;
        }

        public String getSuite() {
            return suite;
        }


        public String getCity() {
            return city;
        }


        public String getZipcode() {
            return zipcode;
        }

        public Geo getGeo() {
            return geo;
        }

    }

    public class Geo {

        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }


    }
}
