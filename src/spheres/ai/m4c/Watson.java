package spheres.ai.m4c;

import java.beans.Customizer;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

import ie.corballis.sox.Sox;

public class Watson {

	private int transaction_id;
	private String customer_name;
	private String credit_card;
	private String agent_name;
	private float amount;
	
	private DateTime date;
	
	private TextToSpeech jennifer;
	
	
	public Watson(String usr,String pwd,int language){
		jennifer = new TextToSpeech(usr, pwd);
	}
		
	
	public String getCurrentDate(){
		date = new DateTime();
		return date.dayOfWeek().getAsText(Locale.ENGLISH) + " , " + date.getDayOfMonth()+" , "  +date.monthOfYear().getAsText(Locale.ENGLISH) +" , "+ date.getYear();
	}
	
	
	public boolean isCreditCardValid(){
		return false;
	}
	
	public void synthesize(String text,String filename){	
		ServiceCall<InputStream> service = jennifer.synthesize(text, Voice.EN_ALLISON,AudioFormat.WAV);
		try {
			InputStream in = WaveUtils.reWriteWaveHeader(service.execute());
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(this.getTransaction_id()+filename+".wav"));
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
			    out.write(buffer, 0, length);
			  }
			  out.close();
			  in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args)
	{
		Watson watson = new Watson("adf006ee-fa5a-4cb8-b58d-01a6f0c53378","HQHEVMPJOXNl",1);
		watson.setTransaction_id(1000);
		watson.setCustomer_name("Monica Smith");
		watson.setAgent_name("Lisa Roberts");
		watson.setAmount(499);
		
		String greeting = "Good afternoon, am I speaking with "+ watson.getCustomer_name()+ " ? ";
			
		String jen = "Hello, my name is " +  watson.getAgent_name() +" with the Verification Department and I am online "
				+ "to confirm your shipping and billing information.  For customer protection and "
				+ "quality assurance purposes, this call will be recorded.  This is not a contest or a "
				+ "drawing, this is a vacation offer from Cancun Vacation Destinations or Cancun Special Promotions, "
				+ "and today´s date is "+ watson.getCurrentDate() +"  .  Please confirm the following contents and terms of your vacation package"
				+ " by stating clearly that you understand everything you are receiving.";

		String rules = "Please understand that if you are married you must be between the ages of 28-76, however,"
				+ "if you are single you must be between the ages of 28-65.  Do you meet these requirements?";
		
		String amount  = "Ok great! Today you are authorizing the amount of "+watson.getAmount() +"  American Dollars for your:";
		
		
		watson.synthesize(greeting,"greeting");
		watson.synthesize(jen,"agent");
		watson.synthesize(rules, "rules");
		watson.synthesize(amount, "amout");
		
		Sox sox = new Sox("");
		sox.inputFile(inputFile)
		
		
		
		
		
		
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCredit_card() {
		return credit_card;
	}

	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	
	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}
