package eu.dasish;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;

// TODO: how to detect encoding??

@Path("/")
public class LangDetectResource {
	
    @GET
    @Path("/{text}")
    @Produces(MediaType.TEXT_PLAIN)
    public String detectFromString(@PathParam("text") String text) throws LangDetectException {
        return detectLanguage(text);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String detectFromFile(@FormParam("text") String text) throws LangDetectException {
        return detectLanguage(text);
    }
    
//    @POST
//    @Path("/upload")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.TEXT_PLAIN)
//    public String detectFromFile(File file) throws IOException, LangDetectException {
//    	InputStream inputStream = new FileInputStream(file);
//    	StringWriter writer = new StringWriter();
//    	IOUtils.copy(inputStream, writer); //, encoding);
//    	String text = writer.toString();
//    	writer.close();
//        return detectLanguage(text);
//    }
    
    private static String detectLanguage(String text) throws LangDetectException {
    	DetectorFactory.loadProfile("profiles");
    	Detector detector = DetectorFactory.create();
    	detector.append(text);
    	String language = detector.detect();
    	DetectorFactory.clear();
        return language;
    }
}
