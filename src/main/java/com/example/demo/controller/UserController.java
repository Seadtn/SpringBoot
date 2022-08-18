package com.example.demo.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.controller.token.ConfirmationToken;
import com.example.demo.controller.token.ConfirmationTokenRepository;
import com.example.demo.exception.ResourceNotFound;
import com.example.demo.model.Concours;
import com.example.demo.model.ConcoursRepository;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionRepository;
import com.example.demo.model.Reponse;
import com.example.demo.model.ReponseRepository;
import com.example.demo.model.UserRepository;
import com.example.demo.model.Utilisateur;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
@RestController
@CrossOrigin("http://localhost:4200")
//@CrossOrigin("http://192.168.1.21:4200")
@RequestMapping("/api/values")
@AllArgsConstructor
public class UserController {
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	private UserRepository userRep;
	@Autowired
	private QuestionRepository quesRep;
	@Autowired
	private ConcoursRepository concoursRep;
	@Autowired
	 private ReponseRepository  repRep;
	BCryptPasswordEncoder   bcpe= new BCryptPasswordEncoder();
	@PostMapping("/register")
   public Utilisateur register(@RequestBody RegistrationRequest request) throws AddressException, MessagingException {
	   return registrationService.register(request);
   }
	@GetMapping("/usr/{email}")
	public Utilisateur getUserById(@PathVariable String email){
		return userRep.findByEmail(email).orElseThrow(()->
        new ResourceNotFound("User n'existe pas "));
		
	}
	@PostMapping("/login")
	public ResponseEntity<Utilisateur> loginUser(@RequestBody Utilisateur userData){
		Utilisateur user= userRep.findByEmail(userData.getEmail())
				.orElseThrow(()->
	            new ResourceNotFound("Utilisateur n'existe pas "));
		boolean result = bcpe.matches(userData.getPassword(),user.getPassword());
		if(result) {
			Utilisateur userExist=userRep.findLoginUser(userData.getEmail(),user.getPassword())
				.orElseThrow(()->
	            new ResourceNotFound("Compte n'existe pas "));
			return ResponseEntity.ok(userExist);}
		return (ResponseEntity<Utilisateur>) ResponseEntity.internalServerError();
	}
	@GetMapping("/login/{id}")
    public ConfirmationToken getUserTokenById(@PathVariable long id){
		ConfirmationToken ct=confirmationTokenRepository.findByIdUser(id).orElseThrow(()->
		            new ResourceNotFound("Token not exist with id="+id));
		return ct;
    }
    @PostMapping("/forgot")
    public Utilisateur VerifCin(@RequestBody Utilisateur userData){
    	Utilisateur userExist=userRep.findByCin(userData.getCin()).orElseThrow(()->
        new ResourceNotFound("User not exist with CIN="+userData.getCin()));
		return userExist;
	}
    @PostMapping("/Forgot/{token}")
    public ConfirmationToken VerifCR(@RequestBody int cin , @PathVariable String token){
    	ConfirmationToken CleExist=confirmationTokenRepository.findTokenByCin(cin,token).orElseThrow(()->
        new ResourceNotFound("Token not exist with CIN="+cin+"/Token="+token));
		return CleExist;
	}
    @PutMapping("/updatePassword/{password}")
    public int UpdatePassword(@RequestBody int cin , @PathVariable("password") String Password){
    	String encodedPass= bcpe.encode(Password);
		return userRep.updatePassword(cin,encodedPass);
	}
    @PostMapping("/concours")
	public ResponseEntity<Concours> createConcours(@RequestParam("concours") String c ,@RequestParam("logo") MultipartFile multi)throws  IOException {
		  String fileName= StringUtils.cleanPath(multi.getOriginalFilename());
		  Concours cs= new ObjectMapper().readValue(c, Concours.class);
		  cs.setLogo(fileName);
		 Concours cr= concoursRep.save(cs);
		  String uploadDir="src/main/webapp/img/" +cr.getId();
		 Path uploadPath= Paths.get(uploadDir);
		 if(!Files.exists(uploadPath)) {
			 Files.createDirectories(uploadPath);
		 }
		 try{
	         InputStream inputStream= multi.getInputStream();
		     Path filePath = uploadPath.resolve(fileName);
		     Files.copy(inputStream,filePath,StandardCopyOption.REPLACE_EXISTING);
		 }catch(IOException e){
			 throw new IOException("Erreur !!");
		 }
		return ResponseEntity.ok(cr); 
		
	}
    @GetMapping("/conc")
	public List<Concours> getConcours(){
		return concoursRep.findAll();
	}
    @PostMapping("/question")
    public Question SaveQuestion(@RequestParam("question") String ques) throws JsonMappingException, JsonProcessingException {
    	Question q= new ObjectMapper().readValue(ques, Question.class);
    	return quesRep.save(q);
    }
    @DeleteMapping("/rows")
     public void RowsEmpty() {
    	quesRep.DeleteRows();
    }
    @DeleteMapping("/deleteConcours")
    public void deleteCr() {
    	   confirmationTokenRepository.deleteToken();
    	   repRep.DeleteReponse();
    	   userRep.deleteUsers();
    	concoursRep.deleteCr();
   }
    @GetMapping("/ques")
	public List<Question> getAllQuestions(){
		return quesRep.findAll();
	}
    @Autowired ServletContext context;
    @GetMapping("/logo")
    public ResponseEntity<String>getLogo(){
    	List<Concours> cr=concoursRep.findAll();
    	Concours cs= cr.get(0);
    	String image = null;
    	String uploadDir=context.getRealPath("img/"+cs.getId()+"/"+cs.getLogo());
    	File fileFolder = new File(uploadDir);
    	if(fileFolder!=null) {
    		if(!fileFolder.isDirectory()) {
    			String encodeBase64=null;
    			try {
    				String extension= FilenameUtils.getExtension(fileFolder.getName());
    				try (FileInputStream fileInputStream = new FileInputStream(fileFolder)) {
						byte[]  bytes = new byte[(int)fileFolder.length()];
						fileInputStream.read(bytes);
						encodeBase64=Base64.getEncoder().encodeToString(bytes);
					}
						image="data:image/"+extension+";base64,"+encodeBase64;
						
    			}catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    		;
    	}
	 return new ResponseEntity<String>(image,HttpStatus.OK);  	
}

	@PostMapping("/inscrir")
	
    public ResponseEntity<Reponse> Inscription(@RequestParam("reponses") List<String> r,@RequestParam("email") String email ,@RequestParam("cv") MultipartFile multi)throws  IOException {
		  String rs= StringUtils.cleanPath(multi.getOriginalFilename());
		  Reponse rep=new Reponse();
		  rep.setCv(rs);

		  Utilisateur user= userRep.findByEmail(email)
					.orElseThrow(()->
		            new ResourceNotFound("Utilisateur n'existe pas "));
		  user.setInscrir(1);
		  rep.setUserapp(user);
		  int i=0;
		  while(i <= r.size()) {
			 switch(i) {
			 case 1:{rep.setReponse1(r.get(0)); break;}
			 case 2:{rep.setReponse2(r.get(1)); break;}
			 case 3:{rep.setReponse3(r.get(2)); break;}
			 case 4:{rep.setReponse4(r.get(3)); break;}
			 case 5:{rep.setReponse5(r.get(4)); break;}
			 case 6:{rep.setReponse6(r.get(5)); break;}
			 case 7:{rep.setReponse7(r.get(6)); break;}
			 case 8:{rep.setReponse8(r.get(7)); break;}
			 case 9:{rep.setReponse9(r.get(8)); break;}
			 case 10:{rep.setReponse10(r.get(9)); break;}
		  
			 }
			 i++;
		  }
		  Reponse cbn= repRep.save(rep);
		 String uploadDir="src/main/webapp/cv/" +cbn.getUserapp().getId();
		 Path uploadPath= Paths.get(uploadDir);
		 if(!Files.exists(uploadPath)) {
			 Files.createDirectories(uploadPath);
		 }
		 
		 try{
	         InputStream inputStream= multi.getInputStream();
		     Path filePath = uploadPath.resolve(rs);
		     Files.copy(inputStream,filePath,StandardCopyOption.REPLACE_EXISTING);
		 }catch(IOException e) {
			 throw new IOException("Erreur !!");
		 }
		return ResponseEntity.ok(cbn); 
		
	}
	@GetMapping("/reps")
	public List<Reponse> getReponses(){
		return repRep.findAll();
	}
	@GetMapping("/dossier/{id}")
    public Reponse getReponseByIdUser(@PathVariable long id){
		Reponse re=repRep.findReponseByIdUser(id).orElseThrow(()->
		            new ResourceNotFound("Reponse not exist with id user="+id));
		return re;
    }
	@RequestMapping("/cv/{id}")
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") long id) throws IOException {
		Reponse re=repRep.findReponseByIdUser(id).orElseThrow(()->
        new ResourceNotFound("Reponse not exist with id user="+id));       
		File file = new File("C:/Users/USER/eclipse-workspace/Concours/src/main/webapp/cv/" +re.getUserapp().getId()+"/"+re.getCv());
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);

			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}
	}
	@PutMapping("/modif")
    public boolean UpdateReponse(@RequestParam("reponses") List<String> r,@RequestParam("id") long id) {
		int i=0;
		String rep1="",rep2="",rep3="",rep4="",rep5="",rep6="",rep7="",rep8="",rep9="",rep10="";
		  while(i <= r.size()) {
			 switch(i) {
			 case 1:{rep1=r.get(0); break;}
			 case 2:{rep2=r.get(1); break;}
			 case 3:{rep3=r.get(2); break;}
			 case 4:{rep4=r.get(3); break;}
			 case 5:{rep5=r.get(4); break;}
			 case 6:{rep6=r.get(5); break;}
			 case 7:{rep7=r.get(6); break;}
			 case 8:{rep8=r.get(7); break;}
			 case 9:{rep9=r.get(8); break;}
			 case 10:{rep10=r.get(9); break;}
		  
			 }
			 i++;
		  }
		  if(r.size()!=0) {
			  repRep.updateReponse(id, rep1, rep2, rep3, rep4, rep5, rep6, rep7, rep8, rep9, rep10);
			  return true;
		  }
		return false;
	}
}
