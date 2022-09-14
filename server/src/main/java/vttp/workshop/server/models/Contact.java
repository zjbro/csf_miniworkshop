package vttp.workshop.server.models;

import java.io.Serializable;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Contact implements Serializable{
    private String contactId;
    private String name;
    private String email;
    private String mobile;

    

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public static Contact create(String json){
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject data = reader.readObject();

        final Contact contact = new Contact();
        contact.setContactId(data.getString("contactId"));
        contact.setName(data.getString("name"));
        contact.setEmail(data.getString("email"));
        contact.setMobile(data.getString("mobile"));

        return contact;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
            .add("contactId", contactId)
            .add("name", name)
            .add("email", email)
            .add("mobile", mobile)
            .build();
    }
    public String getContactId() {
        return contactId;
    }
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String toString() {
        return "Contact [name=" + name + ", email=" + email + ", mobile=" + mobile + ", contactId=" + contactId + "]";
    }
    
    
}
