package ics.common;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URLEncoder;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/common")
public class CommonController {
	
	@GetMapping(value = "/down/{fileName}")
	@ResponseBody
	public ResponseEntity<Resource> download (@PathVariable String fileName, @RequestHeader("User-Agent")String agent) throws Exception{
		String temp = "c:/ics_file/" + fileName;
		File target = new File(temp);
		HttpHeaders header = new HttpHeaders();
		Resource rs = null;
		if(target.exists()) {
			try {
				String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
								
				if(mimeType == null) {
					if(fileName.indexOf(".apk")>0) {
						mimeType ="application/vnd.android.package-archive";
					}else {
						mimeType ="octet-stream";
					}
				}
				
				log.info("파일 다운 로드");
				log.info("fileName : " + fileName);
				log.info("mimeType : " + mimeType);
				
				rs = new UrlResource(target.toURI());
				//String fileName =  cvo.getFile_dtl_origin();
				if(agent.contains("Trident")) {	// 익스
					fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
				}else if(agent.contains("Edge")) { // Edge
					fileName = URLEncoder.encode(fileName, "UTF-8");
				}else { // 크롬
					fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				}
				
				header.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ fileName +"\"");
				header.setCacheControl("no-cache");
				header.setContentType(MediaType.parseMediaType(mimeType));
				
			}catch(Exception e) {
				log.error("download error");
				log.error("param : " + fileName);
				
				e.printStackTrace();
			}
		}
		
		return new ResponseEntity<Resource>(rs, header, HttpStatus.OK);
	}	
	
	/*
	 @Autowired
	 ResourceLoader resourceLoader;

	@GetMapping(value = "/down/{fileName}")
	public ResponseEntity<Resource> resouceFileDownload(@PathVariable String fileName) {
		try {
		Resource resource = resourceLoader.getResource("classpath:static/resources/files/"+ fileName);	
		File file = resource.getFile();	//파일이 없는 경우 fileNotFoundException error가 난다.
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,file.getName())	//다운 받아지는 파일 명 설정
				.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))	//파일 사이즈 설정
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())	//바이너리 데이터로 받아오기 설정
				.body(resource);	//파일 넘기기
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest()
					.body(null);
		} catch (Exception e ) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}
	*/
}
