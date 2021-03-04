package com.example.ExchangeStudentsBackend.web;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.ExchangeStudentsBackend.model.FAQ;
import com.example.ExchangeStudentsBackend.model.FAQRepository;
import com.example.ExchangeStudentsBackend.model.Image;
import com.example.ExchangeStudentsBackend.model.ImageRepository;
import com.example.ExchangeStudentsBackend.model.Request;
import com.example.ExchangeStudentsBackend.model.RequestRepository;

@Controller
public class ExchangeStudentsController {

	@Autowired
	private FAQRepository faqrepo;

	@Autowired
	private ImageRepository imgrepo;

	@Autowired
	private RequestRepository requestrepo;

	// Get all FAQs
	// Only for admin users
	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public @ResponseBody List<FAQ> faqListRest() {
		return (List<FAQ>) faqrepo.findAll();
	}

	// Get answered FAQs
	@RequestMapping(value = "/answeredfaq", method = RequestMethod.GET)
	public @ResponseBody List<FAQ> answeredFaqs() {
		return (List<FAQ>) faqrepo.findByStatus("answered");
	}

	// Add a new FAQ
	@PostMapping("/addfaq")
	public @ResponseBody FAQ newFaq(@RequestBody FAQ newFaq) {
		return faqrepo.save(newFaq);
	}

	// Modify an FAQ
	// Only for admin users
	@PutMapping("/faq/{id}")
	public @ResponseBody FAQ faq(@RequestBody FAQ newFaq, @PathVariable("id") Long faqId) {
		return faqrepo.findById(faqId).map(faq -> {
			faq.setAnswer(newFaq.getAnswer());
			faq.setStatus("answered");
			faq.setTag(newFaq.getTag());
			return faqrepo.save(faq);
		}).orElseGet(() -> {
			newFaq.setId(faqId);
			return faqrepo.save(newFaq);
		});
	}

	// Delete an FAQ
	// Only for admin users
	@DeleteMapping("/faq/{id}")
	public @ResponseBody void deleteFaq(@PathVariable("id") Long faqId) {
		faqrepo.deleteById(faqId);
	}

	// Get all Requests
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public @ResponseBody List<Request> requestList() {
		return (List<Request>) requestrepo.findAll();
	}

	// Add a new Request
	@PostMapping("/addrequest")
	public @ResponseBody Request newRequest(@RequestBody Request newRequest) {
		return requestrepo.save(newRequest);
	}

	@PostMapping("/img")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
		try {
			Image img = new Image();
			img.setName(StringUtils.cleanPath(file.getOriginalFilename()));
			img.setType(file.getContentType());
			img.setData(file.getBytes());

			imgrepo.save(img);

			return ResponseEntity.status(HttpStatus.OK)
					.body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
		}
	}

	@RequestMapping(value = "/allimg", method = RequestMethod.GET)
	public @ResponseBody List<Image> allImages() {
		return (List<Image>) imgrepo.findAll();
	}
	/*
	 * @RequestMapping(value = "/img/{name}", method = RequestMethod.GET)
	 * public @ResponseBody byte[] getImage(@PathVariable("name") String name) {
	 * List<Image> img = imgrepo.findByName(name); InputStream in=
	 * img.get(0).getData(); return IOUtils.toByteArray(in); }
	 */

}
