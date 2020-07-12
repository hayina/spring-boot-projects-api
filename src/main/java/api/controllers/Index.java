package api.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import api.helpers.Helpers;


@Controller
public class Index {

	
	
	@GetMapping(value = { "/", "/projets/**", "/marches/**", "/users/**", "/conventions/**", "/locations/**" })
//	@GetMapping(value = { "/", "/**" })
	public String index() {
		System.out.println("index ...");
//		return "/REACT-APP/index.html";
		return "forward:/index.html";
	}
	
	
	@GetMapping(value = "/attachments/{marche}/{attachType}/download")
	public @ResponseBody void getFile(
				@RequestParam("n") String fileName,
				@RequestParam("d") String date,
				@PathVariable Integer marche,
				@PathVariable String attachType,
				HttpServletResponse response
	) throws IOException {
		
		System.out.println("DOWNLOADING ...");

		response.setHeader("Content-Disposition", "Attachment ; filename="+ fileName );
		
		InputStream in = Files.newInputStream(Paths.get(Helpers.getPathDate(marche, date, attachType) + "/" + fileName));
		ServletOutputStream out = response.getOutputStream();
		
		IOUtils.copy(in, response.getOutputStream());
		
		out.flush();
		in.close();

	}
	
}
