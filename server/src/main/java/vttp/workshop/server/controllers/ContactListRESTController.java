package vttp.workshop.server.controllers;

import java.io.FileInputStream;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp.workshop.server.models.Contact;
import vttp.workshop.server.models.Response;
import vttp.workshop.server.repositories.ContactRepository;
import vttp.workshop.server.services.ContactService;


@RestController
@RequestMapping(path="/api/addressbook", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactListRESTController {

    @Autowired
    private ContactService cService;

    @Autowired
    private ContactRepository cRepo;

    private Logger logger = Logger.getLogger(ContactListRESTController.class.getName());

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addContact(@RequestBody String payload){
        
        Contact contact;
        Response resp;
        JsonArray contactList;

        logger.info("Payload: %s".formatted(payload));

        // JsonArray contacts = null;
        // try (FileInputStream is = new FileInputStream(payload)){
        //     contacts = cService.loadData(is);
		// 	System.out.println("size: " + contacts.size());
        // } catch(Exception ex) {
        //     ex.printStackTrace();
		// 	System.exit(-1);
        // }

        // contacts.stream()
		// 	.map(v -> (JsonObject)v)
		// 	.forEach((JsonObject v) -> {
		// 		cRepo.save(v);
		// 	});




        try{
            
            contact = Contact.create(payload);
            cRepo.save(contact);
            List<String> contactIds = cRepo.findKeys("*");
            contactList = cRepo.contactList(contactIds);

        } catch (Exception ex) {
            resp = new Response();
            resp.setCode(400);
            resp.setMessage(ex.getMessage());
            ex.printStackTrace();;
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resp.toJson().toString());
        }

        resp = new Response();
        resp.setCode(201);
        resp.setMessage(contact.getContactId());
        resp.setData(contactList.toString());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(resp.toJson().toString());
    }




}
