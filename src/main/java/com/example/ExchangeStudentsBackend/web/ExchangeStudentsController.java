package com.example.ExchangeStudentsBackend.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.ExchangeStudentsBackend.model.FAQ;
import com.example.ExchangeStudentsBackend.model.FAQRepository;

@Controller
public class ExchangeStudentsController {

	@Autowired
	private FAQRepository faqrepo;
	
	@RequestMapping(value="/faq", method = RequestMethod.GET)
	public @ResponseBody List<FAQ> faqListRest(){
		return (List<FAQ>) faqrepo.findAll();
	}
	
	@RequestMapping(value="/faq/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<FAQ> faqRest(@PathVariable("id") Long faqId){
		return faqrepo.findById(faqId);
	}
	
	@PostMapping("/addfaq")
	public @ResponseBody FAQ newFaq (@RequestBody FAQ newFaq) {
		//FAQ newFaq = new FAQ(question, "sent");
		return faqrepo.save(newFaq);
	}
	
	
}
