package com.example.ExchangeStudentsBackend.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.ExchangeStudentsBackend.model.FAQ;
import com.example.ExchangeStudentsBackend.model.FAQRepository;

@Controller
public class ExchangeStudentsController {

	@Autowired
	private FAQRepository faqrepo;

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

}
