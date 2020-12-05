package edu.baylor.ecs.rt.controller;
import edu.baylor.ecs.rt.Service.LicenseService;
import edu.baylor.ecs.rt.model.License;
import edu.baylor.ecs.rt.security.payload.response.LicenseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LicenseController {

    @Autowired
    LicenseService licenseService;

    @PostMapping("/licenses")
    @ResponseBody
    public void create(@RequestBody License license) {
        licenseService.saveLicense(license);
    }

    @PostMapping("/licenses/{id}")
    @ResponseBody
    public void edit(@PathVariable(value = "id") long id, @RequestBody License license) {
        licenseService.updateLicense(id, license);
    }


    @GetMapping("/licenses/{id}")
    @ResponseBody
    public ResponseEntity<License> findById(@PathVariable(value = "id") long id) {
        License license = licenseService.findById(id);
        return new ResponseEntity(license, HttpStatus.OK);
    }

    @GetMapping("/licenses/")
    @ResponseBody
    public ResponseEntity<List<License>> findAll() {
        List<LicenseResponse> licenses = licenseService.findAll();
        return new ResponseEntity(licenses, HttpStatus.OK);
    }

    @GetMapping("/licenses/user/{userid}")
    @ResponseBody
    public ResponseEntity<LicenseResponse> findByUserId(@PathVariable(value = "userid") long userid) {
        //List<License> license = licenseService.findByUserId(userid);
        List<LicenseResponse> responses = licenseService.findByUserId(userid);
        return new ResponseEntity(responses, HttpStatus.OK);
    }
    
    @GetMapping("/alllicenses")
    @ResponseBody
    public ResponseEntity<LicenseResponse> findall() {
        //List<License> license = licenseService.findByUserId(userid);
        List<LicenseResponse> responses = licenseService.findAll();
        return new ResponseEntity(responses, HttpStatus.OK);
    }
    
    @GetMapping("/licenseCategories")
    @ResponseBody
    public ResponseEntity<LicenseResponse> findallLicenseCategories() {
        
        List<License> responses = licenseService.findallLicenseCategories();
        return new ResponseEntity(responses, HttpStatus.OK);
    }
}

