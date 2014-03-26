import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendJSON {

	public static void main(String args[]) throws JSONException, IOException {

		send();
	}

	@SuppressWarnings("deprecation")
	public static void send() throws JSONException, IOException {
		
		//put your PDF file name here
		String pdfFile = "";
		

		//convert pdf to byte array
		byte[] binaryData = convertPDFtoByteArray(pdfFile);
		
		//convert byte array to base64 string
		String pdf = Base64.encodeBase64String(binaryData);
		
		//enter your information
		String first_name = "";
		String last_name = "";
		String email = "";
		String position_id = "";
		String explanation = "";
		String source = "";
		String resume = "";

		JSONArray projects = new JSONArray();
		projects.put("");

		JSONObject json = new JSONObject();
		json.put("first_name", first_name);
		json.put("last_name", last_name);
		json.put("email", email);
		json.put("position_id", position_id);
		json.put("explanation", explanation);
		json.put("projects", projects);
		json.put("source", source);
		json.put("resume", pdf);

		@SuppressWarnings("resource")
		HttpClient  httpClient = new DefaultHttpClient();
		String url = "https://getperka.com/api/2/apply";

		try {
			HttpPost request = new HttpPost(url);
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type",
					"application/json");
			request.setEntity(params);
			HttpResponse hr = httpClient.execute(request);

			// handle response here...
			System.out.println(json.toString());
			System.out.println(hr.getStatusLine());
			System.out.println(hr.getEntity());
			
		} catch (Exception ex) {
			// handle exception here
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

	}
	
	
	public static byte[] convertPDFtoByteArray(String fileName) throws FileNotFoundException{

		File file = new File("Saikit Chan.pdf");
		 
        FileInputStream fis = new FileInputStream(file); 
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        	
        } 
		
        return bos.toByteArray();
	}
}
