package com.example.ExchangeStudentsBackend.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.ExchangeStudentsBackend.model.Chat;
import com.example.ExchangeStudentsBackend.model.ChatRepository;
import com.example.ExchangeStudentsBackend.model.FAQ;
import com.example.ExchangeStudentsBackend.model.FAQRepository;
import com.example.ExchangeStudentsBackend.model.Image;
import com.example.ExchangeStudentsBackend.model.ImageRepository;
import com.example.ExchangeStudentsBackend.model.Offer;
import com.example.ExchangeStudentsBackend.model.OfferRepository;
import com.example.ExchangeStudentsBackend.model.Request;
import com.example.ExchangeStudentsBackend.model.RequestRepository;
import com.example.ExchangeStudentsBackend.model.Topic;
import com.example.ExchangeStudentsBackend.model.TopicRepository;

@Controller
public class ExchangeStudentsController {

	@Autowired
	private FAQRepository faqrepo;

	@Autowired
	private ImageRepository imgrepo;

	@Autowired
	private RequestRepository requestrepo;

	@Autowired
	private OfferRepository offerrepo;

	@Autowired
	private ChatRepository chatrepo;

	@Autowired
	private TopicRepository topicrepo;

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
	public @ResponseBody Request newRequest(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("desc") String desc, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("location") String location) {

		try {
			Image img = new Image();
			img.setName(StringUtils.cleanPath(file.getOriginalFilename()));
			img.setType(file.getContentType());
			img.setData(file.getBytes());

			Image savedImg = imgrepo.save(img);

			Request newRequest = new Request(name, desc, phoneNumber, location, savedImg.getId());
			return requestrepo.save(newRequest);

		} catch (Exception e) {
			return null;
		}
	}

	// Delete a Request
	@DeleteMapping("/request/{id}")
	public @ResponseBody void deleteRequest(@PathVariable("id") Long requestId) {
		Optional<Request> req = requestrepo.findById(requestId);
		imgrepo.deleteById(req.get().getImgId());
		requestrepo.deleteById(requestId);
	}

	// Get all Offers
	@RequestMapping(value = "/offer", method = RequestMethod.GET)
	public @ResponseBody List<Offer> offerList() {
		return (List<Offer>) offerrepo.findAll();
	}

	// Add a new Offer
	@PostMapping("/addoffer")
	public @ResponseBody Offer newOffer(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("desc") String desc, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("location") String location, double price) {

		try {
			Image img = new Image();
			img.setName(StringUtils.cleanPath(file.getOriginalFilename()));
			img.setType(file.getContentType());
			img.setData(file.getBytes());

			Image savedImg = imgrepo.save(img);

			Offer newOffer = new Offer(name, desc, phoneNumber, location, savedImg.getId(), price);
			return offerrepo.save(newOffer);

		} catch (Exception e) {
			return null;
		}
	}

	// Delete an Offer
	@DeleteMapping("/offer/{id}")
	public @ResponseBody void deleteOffer(@PathVariable("id") Long offerId) {
		Optional<Offer> offer = offerrepo.findById(offerId);
		imgrepo.deleteById(offer.get().getImgId());
		offerrepo.deleteById(offerId);
	}

	/*
	 * @RequestMapping(value = "/allimg", method = RequestMethod.GET)
	 * public @ResponseBody List<Image> allImages() { return (List<Image>)
	 * imgrepo.findAll(); }
	 */

	// Get an Image
	@RequestMapping(value = "/img/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImage(@PathVariable("id") Long imgId) {
		Optional<Image> img = imgrepo.findById(imgId);
		return img.get().getData();
	}

	// Get all topics
	@RequestMapping(value = "/topic", method = RequestMethod.GET)
	public @ResponseBody List<Topic> topicList() {
		return (List<Topic>) topicrepo.findAll();
	}

	// Add a new Topic
	@PostMapping("/addtopic")
	public @ResponseBody Topic newTopic(@RequestBody Topic newTopic) {
		return topicrepo.save(newTopic);
	}

	// Add a new Chat
	@PostMapping("/addchat")
	public @ResponseBody Chat newChat(@RequestBody Chat newChat) {
		return chatrepo.save(newChat);
	}
}
