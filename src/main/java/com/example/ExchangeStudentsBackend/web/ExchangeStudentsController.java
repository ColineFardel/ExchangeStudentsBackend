package com.example.ExchangeStudentsBackend.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.ExchangeStudentsBackend.JwtTokenUtil;
import com.example.ExchangeStudentsBackend.model.Chat;
import com.example.ExchangeStudentsBackend.model.ChatRepository;
import com.example.ExchangeStudentsBackend.model.ChatResponse;
import com.example.ExchangeStudentsBackend.model.Course;
import com.example.ExchangeStudentsBackend.model.CourseRepository;
import com.example.ExchangeStudentsBackend.model.Event;
import com.example.ExchangeStudentsBackend.model.EventRepository;
import com.example.ExchangeStudentsBackend.model.EventResponse;
import com.example.ExchangeStudentsBackend.model.FAQ;
import com.example.ExchangeStudentsBackend.model.FAQRepository;
import com.example.ExchangeStudentsBackend.model.Image;
import com.example.ExchangeStudentsBackend.model.ImageRepository;
import com.example.ExchangeStudentsBackend.model.JwtRequest;
import com.example.ExchangeStudentsBackend.model.JwtResponse;
import com.example.ExchangeStudentsBackend.model.Offer;
import com.example.ExchangeStudentsBackend.model.OfferRepository;
import com.example.ExchangeStudentsBackend.model.Request;
import com.example.ExchangeStudentsBackend.model.RequestRepository;
import com.example.ExchangeStudentsBackend.model.Tip;
import com.example.ExchangeStudentsBackend.model.TipRepository;
import com.example.ExchangeStudentsBackend.model.Topic;
import com.example.ExchangeStudentsBackend.model.TopicRepository;
import com.example.ExchangeStudentsBackend.model.UniResponse;
import com.example.ExchangeStudentsBackend.model.User;
import com.example.ExchangeStudentsBackend.model.UserObjectsResponse;
import com.example.ExchangeStudentsBackend.model.UserRepository;

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
	@Autowired
	private EventRepository eventrepo;
	@Autowired
	private UserRepository userrepo;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;

	// ********BEGIN CALLS FOR AUTHENTICATION********

	@RequestMapping(value = "/auth/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@PostMapping(value = "/auth/signup")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (userrepo.existsByUsername(user.getUsername()) || userrepo.existsByEmail(user.getEmail())) {
			return ResponseEntity.badRequest().body("Username or Email already used");
		} else {
			user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
			userrepo.save(user);
			return ResponseEntity.ok("Signed up");
		}
	}

	// Get one user
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public @ResponseBody User getUser(@RequestBody User user) {
		User userResponse = userrepo.findByUsername(user.getUsername());
		return userResponse;
	}

	// Delete a user
	// Only for admin users
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/user/{id}")
	public @ResponseBody void deleteUser(@PathVariable("id") Long userId) {
		userrepo.deleteById(userId);
	}

	// Get all user's created objects
	@RequestMapping(value = "/userObjects", method = RequestMethod.GET)
	public @ResponseBody UserObjectsResponse getUserObjects(@RequestBody String username) {
		User user = userrepo.findByUsername(username);
		return new UserObjectsResponse(user.getEvents(), user.getTips(), user.getOffers(), user.getRequests());
	}

	// Get user chats
	@RequestMapping(value = "/userChats", method = RequestMethod.GET)
	public @ResponseBody List<Chat> getUserChats(@RequestBody String username) {
		User user = userrepo.findByUsername(username);
		return user.getChats();
	}

	// ********END CALLS FOR AUTHENTICATION********

	// ********BEGIN FAQ CALLS********

	// Get all FAQs
	// Only for admin users
	@PreAuthorize("hasAuthority('ADMIN')")
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
	@PreAuthorize("hasAuthority('ADMIN')")
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
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/faq/{id}")
	public @ResponseBody void deleteFaq(@PathVariable("id") Long faqId) {
		faqrepo.deleteById(faqId);
	}

	// ********END FAQ CALLS********

	// ********BEGIN MARKET CALLS********

	// Get all Requests
	@RequestMapping(value = "/requests", method = RequestMethod.GET)
	public @ResponseBody List<Request> requestList() {
		return (List<Request>) requestrepo.findAll();
	}

	// Add a new Request
	@PostMapping("/addrequest")
	public @ResponseBody Request newRequest(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("desc") String desc, @RequestParam("location") String location,
			@RequestParam("user") User user) {

		try {
			Image img = new Image();
			img.setName(StringUtils.cleanPath(file.getOriginalFilename()));
			img.setType(file.getContentType());
			img.setData(file.getBytes());

			Image savedImg = imgrepo.save(img);

			Request newRequest = new Request(name, desc, location, savedImg.getId(), user);
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

	// Get all requests' locations
	@RequestMapping(value = "/requestsloc", method = RequestMethod.GET)
	public @ResponseBody List<String> requestsLocList() {
		List<Request> requests = (List<Request>) requestrepo.findAll();
		List<String> locs = new ArrayList<String>();
		for (Request request : requests) {
			String loc = request.getLocation();
			if (!locs.contains(loc)) {
				locs.add(loc);
			}
		}
		return locs;
	}

	// Get all Offers
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public @ResponseBody List<Offer> offerList() {
		return (List<Offer>) offerrepo.findAll();
	}

	// Add a new Offer
	@PostMapping("/addoffer")
	public @ResponseBody Offer newOffer(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("desc") String desc, @RequestParam("location") String location, double price,
			@RequestParam("user") User user) {

		try {
			Image img = new Image();
			img.setName(StringUtils.cleanPath(file.getOriginalFilename()));
			img.setType(file.getContentType());
			img.setData(file.getBytes());

			Image savedImg = imgrepo.save(img);

			Offer newOffer = new Offer(name, desc, location, savedImg.getId(), price, user);
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

	// Get all offers' locations
	@RequestMapping(value = "/offersloc", method = RequestMethod.GET)
	public @ResponseBody List<String> offersLocList() {
		List<Offer> offers = (List<Offer>) offerrepo.findAll();
		List<String> locs = new ArrayList<String>();
		for (Offer offer : offers) {
			String loc = offer.getLocation();
			if (!locs.contains(loc)) {
				locs.add(loc);
			}
		}
		return locs;
	}

	// ********END MARKET CALLS********

	// ********BEGIN TOPIC CALLS********

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
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/topic/{id}")
	public @ResponseBody void deleteTopic(@PathVariable("id") Long topicId) {
		topicrepo.deleteById(topicId);
	}

	// ********END TOPIC CALLS********

	// ********BEGIN CHAT CALLS********

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

	// Get chats from one topic sorted by date
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

	// ********END CHAT CALLS********

	// ********BEGIN COURSE CALLS********

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
	@PreAuthorize("hasAuthority('ADMIN')")
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

	// ********END COURSE CALLS********

	// ********BEGIN TIP CALLS********

	// Get all Tips
	@RequestMapping(value = "/tips", method = RequestMethod.GET)
	public @ResponseBody List<Tip> tipsList() {
		return (List<Tip>) tiprepo.findAll();
	}

	// Add a new Tip with image
	@PostMapping("/addtipwithimg")
	public @ResponseBody Tip newTip(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("desc") String desc, @RequestParam("tag") String tag,
			@RequestParam("location") String location, @RequestParam("user") User user) {

		try {
			Image img = new Image();
			img.setName(StringUtils.cleanPath(file.getOriginalFilename()));
			img.setType(file.getContentType());
			img.setData(file.getBytes());

			Tip newTip;

			Image savedImg = imgrepo.save(img);
			if (location.isEmpty()) {
				newTip = new Tip(name, desc, tag, savedImg.getId(), user);
			} else {
				newTip = new Tip(name, desc, tag, location, savedImg.getId(), user);
			}

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
		if (tip.get().getImgId() > 0) {
			imgrepo.deleteById(tip.get().getImgId());
		}
		tiprepo.deleteById(tipId);
	}

	// ********END TIP CALLS********

	// ********BEGIN EVENT CALLS********

	// Get all Events
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public @ResponseBody List<Event> eventsList() {
		return (List<Event>) eventrepo.findAll();
	}

	// Get Events with date as title
	@RequestMapping(value = "/eventsByDate", method = RequestMethod.GET)
	public @ResponseBody List<EventResponse> eventsByDate() {
		List<Event> events = (List<Event>) eventrepo.findAll();
		List<String> temp = new ArrayList<String>();
		List<EventResponse> eventsWithTitle = new ArrayList<EventResponse>();
		if (!events.isEmpty()) {
			for (Event event : events) {
				if (!temp.contains(event.getDate())) {
					temp.add(event.getDate());
				}
			}
			for (String date : temp) {
				eventsWithTitle.add(new EventResponse(date, eventrepo.findByDate(date)));
			}
		}
		return eventsWithTitle;
	}

	// Add a new Event
	@PostMapping("/addevent")
	public @ResponseBody Event newEvent(@RequestBody Event newEvent) {
		User user = userrepo.findByUsername(newEvent.getUser().getUsername());
		newEvent.setUser(user);
		return eventrepo.save(newEvent);
	}

	// Delete an Event
	@DeleteMapping("/event/{id}")
	public @ResponseBody void deleteEvent(@PathVariable("id") Long eventId) {
		eventrepo.deleteById(eventId);
	}

	// ********END EVENT CALLS********

	// Get an Image
	@RequestMapping(value = "/img/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImage(@PathVariable("id") Long imgId) {
		Optional<Image> img = imgrepo.findById(imgId);
		return img.get().getData();
	}
}
