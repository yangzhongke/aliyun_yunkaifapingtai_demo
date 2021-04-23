package com.alibaba.sca.temp.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.nls.client.AccessToken;
import com.alibaba.sca.services.HttpUtil;
import com.alibaba.sca.vo.Word;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Value("${aliyun.ai.speech.appKey}")
    private String appKey;

    @Value("${aliyun.ai.speech.accessKey_ID}")
    private String accessKey_ID;

    @Value("${aliyun.ai.speech.accessKey_Secret}")
    private String accessKey_Secret;

    @RequestMapping("/api/getAnyWord")
    @ResponseBody
    public Word getAnyWord() {
      ArrayList<Word> words = new ArrayList<>();
      words.add(new Word("hello","你好"));  
      words.add(new Word("apple","苹果"));  
      words.add(new Word("yes","是的"));  
      words.add(new Word("no","不"));  
      words.add(new Word("you","你"));
      Random rand = new Random();
      Word word = words.get(rand.nextInt(words.size()));
      return word;
    }

    @RequestMapping(value="/api/Mp3ToSrt", produces="application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<String> Mp3ToSrt(@RequestParam("file") MultipartFile file, 
      HttpServletResponse response)
    {
      String srtFileName = FilenameUtils.getBaseName(file.getOriginalFilename())+".srt";

      /*
      String appKey = "Jy4tTt82k1XKr1j2";
      String accessKey_ID = "LTAI5t989qtUJSWdkMwZe6Fn";
      String accessKey_Secret = "HdowfDEU5vsiGc1AtpBPPCwBPxmLnd";*/
      String format="mp3";
      int sampleRate = 16000;
      AccessToken token = new AccessToken(accessKey_ID, accessKey_Secret);
     
      try 
      {
         token.apply();
        String accessToken = token.getToken();

        byte[] content = file.getBytes();
        String url = "https://nls-gateway.cn-shanghai.aliyuncs.com/stream/v1/FlashRecognizer";
        url+= "?appkey=" + appKey;
        url+= "&token=" + accessToken;
        url+= "&format=" + format;
        url+= "&sample_rate=" + sampleRate;
        String recResponse = HttpUtil.sendPostFile(url,content, new HashMap<String, String>());

        JsonObject jsonObj = JsonParser.parseString(recResponse).getAsJsonObject();
        JsonObject jsonFlashResult = jsonObj.get("flash_result").getAsJsonObject();
        int duration = jsonFlashResult.get("duration").getAsInt();
        JsonArray jsonSentences = jsonFlashResult.get("sentences").getAsJsonArray();
        String text = jsonSentences.get(0).getAsJsonObject().get("text").getAsString();
        String[] sentences = text.split(",|\\?|\\.|\\!");
        long nanoPerSentence = duration*1_000_000L/sentences.length;
        StringBuilder sbSrt = new StringBuilder();
        for(int i=0;i<sentences.length;i++)
        {
            String sentence = sentences[i];
            LocalTime begin = LocalTime.ofNanoOfDay(i*nanoPerSentence);
            LocalTime end = LocalTime.ofNanoOfDay((i+1)*nanoPerSentence);
            sbSrt.append(i+1).append("\r\n");
            sbSrt.append(begin.toString()).append(" --> ").append(end.toString()).append("\r\n");
            sbSrt.append(sentence).append("\r\n");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(srtFileName,"UTF-8"));
        return new HttpEntity<String>(sbSrt.toString(), headers);

      } catch (IOException e) 
      {
        throw new RuntimeException(e);
      }
    }	
}
