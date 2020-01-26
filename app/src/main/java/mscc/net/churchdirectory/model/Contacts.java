package mscc.net.churchdirectory.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Dany on 01-01-2018.
 * Contact details for church members
 */

public class Contacts implements Parcelable {

    private int id;
    private String name;
    private String phone;
    private String address;
    private String prayerGroup;
    private String permanentAddress;
    private String homeParish;
    private List<Member> members;
    private List<ContactDetails> contactDetails;

    protected Contacts(Parcel in) {
        id = in.readInt();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        prayerGroup = in.readString();
        permanentAddress = in.readString();
        homeParish = in.readString();
        members = in.createTypedArrayList(Member.CREATOR);
        contactDetails = in.createTypedArrayList(ContactDetails.CREATOR);
    }

    public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
        @Override
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        @Override
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrayerGroup() {
        return prayerGroup;
    }

    public void setPrayerGroup(String prayerGroup) {
        this.prayerGroup = prayerGroup;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getHomeParish() {
        return homeParish;
    }

    public void setHomeParish(String homeParish) {
        this.homeParish = homeParish;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<ContactDetails> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(List<ContactDetails> contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Contacts(int id, String name, String phone, String address, String prayerGroup, String permanentAddress, String homeParish, List<Member> members, List<ContactDetails> contactDetails) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.prayerGroup = prayerGroup;
        this.permanentAddress = permanentAddress;
        this.homeParish = homeParish;
        this.members = members;
        this.contactDetails = contactDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(prayerGroup);
        dest.writeString(permanentAddress);
        dest.writeString(homeParish);
        dest.writeTypedList(members);
        dest.writeTypedList(contactDetails);
    }


    public static class Member implements Parcelable {
        private String name;
        private String relation;
        private String dob;
        private String bloodGroup;

        public Member(String name, String relation, String dob, String bloodGroup) {
            this.name = name;
            this.relation = relation;
            this.dob = dob;
            this.bloodGroup = bloodGroup;
        }

        protected Member(Parcel in) {
            name = in.readString();
            relation = in.readString();
            dob = in.readString();
            bloodGroup = in.readString();
        }

        public static final Creator<Member> CREATOR = new Creator<Member>() {
            @Override
            public Member createFromParcel(Parcel in) {
                return new Member(in);
            }

            @Override
            public Member[] newArray(int size) {
                return new Member[size];
            }
        };

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(relation);
            dest.writeString(dob);
            dest.writeString(bloodGroup);
        }
    }

    public static class ContactDetails implements Parcelable {
        private String type;
        private String name;
        private String number;
        private String address;

        public ContactDetails(String type, String name, String number, String address) {
            this.type = type;
            this.name = name;
            this.number = number;
            this.address = address;
        }

        protected ContactDetails(Parcel in) {
            type = in.readString();
            name = in.readString();
            number = in.readString();
            address = in.readString();
        }

        public static final Creator<ContactDetails> CREATOR = new Creator<ContactDetails>() {
            @Override
            public ContactDetails createFromParcel(Parcel in) {
                return new ContactDetails(in);
            }

            @Override
            public ContactDetails[] newArray(int size) {
                return new ContactDetails[size];
            }
        };

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(type);
            dest.writeString(name);
            dest.writeString(number);
            dest.writeString(address);
        }
    }

}
