package com.example.ExchangeStudentsBackend.web;

import java.util.ArrayList;
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
import com.example.ExchangeStudentsBackend.model.ChatResponse;
import com.example.ExchangeStudentsBackend.model.Course;
import com.example.ExchangeStudentsBackend.model.CourseRepository;
import com.example.ExchangeStudentsBackend.model.FAQ;
import com.example.ExchangeStudentsBackend.model.FAQRepository;
import com.example.ExchangeStudentsBackend.model.Image;
import com.example.ExchangeStudentsBackend.model.ImageRepository;
import com.example.ExchangeStudentsBackend.model.Offer;
import com.example.ExchangeStudentsBackend.model.OfferRepository;
import com.example.ExchangeStudentsBackend.model.Request;
import com.example.ExchangeStudentsBackend.model.RequestRepository;
import com.example.ExchangeStudentsBackend.model.Tip;
import com.example.ExchangeStudentsBackend.model.TipRepository;
import com.example.ExchangeStudentsBackend.model.Topic;
import com.example.ExchangeStudentsBackend.model.TopicRepository;
import com.example.ExchangeStudentsBackend.model.UniResponse;

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

	@Autowired
	private CourseRepository courserepo;

	@Autowired
	private TipRepository tiprepo;

	// Get all FAQs
	// Only for admin users
	@RequestMapping(value = "/faqs", method = RequestMethod.GET)
	public @ResponseBody List<FAQ> faqListRest() {
		return (List<FAQ>) faqrepo.findAll();
	}

	// Get answered FAQs
	@RequestMapping(value = "/answeredfaqs", method = RequestMethod.GET)
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
	@RequestMapping(value = "/requests", method = RequestMethod.GET)
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
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
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
	@RequestMapping(value = "/topics", method = RequestMethod.GET)
	public @ResponseBody List<Topic> topicList() {
		return (List<Topic>) topicrepo.findAll();
	}

	// Add a new Topic
	@PostMapping("/addtopic")
	public @ResponseBody Topic newTopic(@RequestBody Topic newTopic) {
		return topicrepo.save(newTopic);
	}

	// Delete a Topic
	// Only for admin
	@DeleteMapping("/topic/{id}")
	public @ResponseBody void deleteTopic(@PathVariable("id") Long topicId) {
		topicrepo.deleteById(topicId);
	}

	// Add a new Chat
	@PostMapping("/addchat")
	public @ResponseBody Chat newChat(@RequestBody Chat newChat) {
		return chatrepo.save(newChat);
	}

	// Get chats from one topic
	@RequestMapping(value = "/chat/topic/{id}", method = RequestMethod.GET)
	public @ResponseBody List<Chat> topicchatList(@PathVariable("id") Long topicId) {
		return (List<Chat>) chatrepo.findByTopic(topicrepo.findById(topicId).get());
	}

	// Get chats from one topic
	@RequestMapping(value = "/chatByDate/topic/{id}", method = RequestMethod.GET)
	public @ResponseBody List<ChatResponse> topicChatWithTitle(@PathVariable("id") Long topicId) {
		List<Chat> chats = chatrepo.findByTopic(topicrepo.findById(topicId).get());
		List<String> temp = new ArrayList<String>();
		List<ChatResponse> chatsWithTitle = new ArrayList<ChatResponse>();
		if (!chats.isEmpty()) {
			for (Chat chat : chats) {
				if (!temp.contains(chat.getDate())) {
					temp.add(chat.getDate());
				}
			}
			for (String date : temp) {
				chatsWithTitle.add(
						new ChatResponse(date, chatrepo.findByTopicAndDate(topicrepo.findById(topicId).get(), date)));
			}
		}
		return chatsWithTitle;
	}

	// Get chats from one course
	@RequestMapping(value = "/chat/course/{id}", method = RequestMethod.GET)
	public @ResponseBody List<Chat> coursechatList(@PathVariable("id") Long courseId) {
		return (List<Chat>) chatrepo.findByCourse(courserepo.findById(courseId).get());
	}

	// Get chats from one course
	@RequestMapping(value = "/chatByDate/course/{id}", method = RequestMethod.GET)
	public @ResponseBody List<ChatResponse> courseChatWithTitle(@PathVariable("id") Long courseId) {
		List<Chat> chats = chatrepo.findByCourse(courserepo.findById(courseId).get());
		List<String> temp = new ArrayList<String>();
		List<ChatResponse> chatsWithTitle = new ArrayList<ChatResponse>();
		if (!chats.isEmpty()) {
			for (Chat chat : chats) {
				if (!temp.contains(chat.getDate())) {
					temp.add(chat.getDate());
				}
			}
			for (String date : temp) {
				chatsWithTitle.add(new ChatResponse(date,
						chatrepo.findByCourseAndDate(courserepo.findById(courseId).get(), date)));
			}
		}
		return chatsWithTitle;
	}

	// Get all courses
	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public @ResponseBody List<Course> courseList() {
		return (List<Course>) courserepo.findAll();
	}

	// Add a new Course
	@PostMapping("/addcourse")
	public @ResponseBody Course newCourse(@RequestBody Course newCourse) {
		return courserepo.save(newCourse);
	}

	// Delete a Course
	// Only for admin
	@DeleteMapping("/course/{id}")
	public @ResponseBody void deleteCourse(@PathVariable("id") Long courseId) {
		courserepo.deleteById(courseId);
	}

	// Modify a course
	@PutMapping("/course/{id}")
	public @ResponseBody Course course(@RequestBody Course newCourse, @PathVariable("id") Long courseId) {
		return courserepo.findById(courseId).map(course -> {
			course.setName(newCourse.getName());
			course.setTeacher(newCourse.getTeacher());
			course.setUniversity(newCourse.getUniversity());
			return courserepo.save(course);
		}).orElseGet(() -> {
			newCourse.setId(courseId);
			return courserepo.save(newCourse);
		});
	}

	// Get all universities
	@RequestMapping(value = "/universities", method = RequestMethod.GET)
	public @ResponseBody List<UniResponse> uniList() {
		List<Course> courses = (List<Course>) courserepo.findAll();
		List<UniResponse> universitiesResponse = new ArrayList<UniResponse>();
		List<String> universities = new ArrayList<String>();
		for (Course course : courses) {
			String uni = course.getUniversity();
			if (!universities.contains(uni)) {
				universities.add(uni);
				universitiesResponse.add(new UniResponse(uni, uni));
			}
		}
		return universitiesResponse;
	}

	// Get all Tips
	@RequestMapping(value = "/tips", method = RequestMethod.GET)
	public @ResponseBody List<Tip> tipsList() {
		return (List<Tip>) tiprepo.findAll();
	}

	// Add a new Tip
	@PostMapping("/addtipwithimg")
	public @ResponseBody Tip newTip(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("desc") String desc, @RequestParam("tag") String tag,
			@RequestParam("location") String location) {

		try {
			Image img = new Image();
			img.setName(StringUtils.cleanPath(file.getOriginalFilename()));
			img.setType(file.getContentType());
			img.setData(file.getBytes());

			Image savedImg = imgrepo.save(img);

			Tip newTip = new Tip(name, desc, tag, location, savedImg.getId());
			return tiprepo.save(newTip);

		} catch (Exception e) {
			return null;
		}
	}

	// Add a new Tip
	@PostMapping("/addtip")
	public @ResponseBody Tip newTip(@RequestBody Tip newTip) {
		return tiprepo.save(newTip);
	}

	// Delete a Tip
	@DeleteMapping("/tip/{id}")
	public @ResponseBody void deleteTip(@PathVariable("id") Long tipId) {
		Optional<Tip> tip = tiprepo.findById(tipId);
		imgrepo.deleteById(tip.get().getImg());
		offerrepo.deleteById(tipId);
	}
}
