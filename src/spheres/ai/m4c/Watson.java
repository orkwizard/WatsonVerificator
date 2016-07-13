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
import org.joda.time.Interval;

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
	private int laguna_days;
	private int laguna_nights;
	private int htr_days;
	private int htr_nigths;
	private DateTime date;
	private TextToSpeech jennifer;
	
	
	
	
	
	
	public Watson(String usr,String pwd,int language){
		jennifer = new TextToSpeech(usr, pwd);
	}
		
	
	public String getCurrentDate(){
		date = new DateTime();
		return date.dayOfWeek().getAsText(Locale.ENGLISH) + " , " + date.getDayOfMonth()+" , "  +date.monthOfYear().getAsText(Locale.ENGLISH) +" , "+ date.getYear();
	}
	
	public String getCurrentTime(){
		
		final DateTime testDate = new DateTime();
		final DateTime eightAM = testDate.withTime(8, 0, 0, 0);
		final DateTime twelveAM = testDate.withTime(12, 0, 0, 0);
		final DateTime fivePM = testDate.withTime(17, 0, 0, 0);
		final DateTime sevenPM = testDate.withTime(19, 0, 0, 0);
		 
		final Interval morning = new Interval(eightAM, twelveAM);
		final Interval afternoon = new Interval(twelveAM, fivePM);
		final Interval earlyEvening = new Interval(fivePM, sevenPM);
		 
		System.out.println("Morning: " + morning.contains(testDate));
		System.out.println("Afternoon: " + afternoon.contains(testDate));
		System.out.println("Evening: " + earlyEvening.contains(testDate));
		System.out.println("Morning Before Evening: " + morning.isBefore(afternoon));
		
		
		
		date = new DateTime();
		int hour = date.getHourOfDay();
		if(hour>12)
			return "Good Afternoon";
		else
			return "Good Morning";
		
		
		
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
		watson.setCustomer_name("MR. ORLANDO ARROYO");
		watson.setAgent_name("LISA THOMPSON. I'm an AI Verification Robot, so please follow my instructions carefully as I am not that smart.");
		watson.setAmount(499);
		
		String greeting = watson.getCurrentTime() + ", am I speaking with "+ watson.getCustomer_name()+ " ? ";
			
		String jen = "Hello, my name is " +  watson.getAgent_name() +" Today I'm with you "
				+ "to confirm your shipping and billing information.  For customer protection and "
				+ "quality assurance purposes, this call will be recorded.  This is not a contest or a "
				+ "drawing, this is a vacation offer from Cancun Vacation Destinations or Cancun Special Promotions, "
				+ "and today´s date is "+ watson.getCurrentDate() +", Please confirm the following contents and terms of your vacation package"
				+ " by stating clearly that you understand everything you are receiving.";

		String rules = "Please understand that if you are married you must be between the ages of 28-76, however,"
				+ "if you are single you must be between the ages of 28-65.  If  you meet these requirements say YES and DIAL 1 if NO say it and dial 2";
		
		String amount  = "Ok great! Today you are authorizing the amount of "+watson.getAmount() +"  American Dollars for your:";
		
		String laguna_suites = watson.getLaguna_days() +  " days and" + watson.getLaguna_nights()  +" nights of All Inclusive accommodations at the Laguna Suites Golf & Spa or The Ocean Spa Hotel in Cancun, Mexico.";
		String htr = watson.getHtr_days()+" days and "+watson.getHtr_nigths() +" nights of All Inclusive accommodations at the Hacienda Tres Rios in the Riviera Maya, Mexico."; 
		
		String includes =  "	Your Cancun / Riviera Maya vacation is valid for 2 adults and 2 children 12 and under."+
						   "	You are also receiving 5 days and 4 nights of accommodations in Orlando, Florida at a Walt Disney World Good Neighbour Hotel, such as The Inn at Calypso, or The Crown Club Inn at Summer Bay.  Your Florida vacation is valid for 4 people."+  
						   "	You are also receiving 5 days and 4 nights of accommodations in Ft. Lauderdale at Blue Strawberry by the Sea.  Your Ft. Lauderdale vacation is valid for up to 4 adults and 2 children 17 and under."+
						   "    You are also receiving a certificate for an All Inclusive Cruise for 2 adults for 4 days and 3 nights with Carnival or Caribbean Cruise Lines.  Port fees, additional taxes, and alcoholic beverages are NOT included with this certificate."+ 
						   "	You are also receiving 5 days and 4 nights of All Inclusive accommodations at the Villas Sol Hotel and Beach Resort in Costa Rica.  Your Costa Rica vacation is valid for 2 adults and 2 children 5 and under. "+ 
						   "	You will also be receiving a Dream Week Certificate which entitles you to 8 days and 7 nights of condo style accommodations at destinations around the world.  Your Dream Week Certificate is valid for up to 6 people."+
						   "	You will also be receiving a certificate which entitles you to 3 days and 2 nights of accommodations at over 40 destinations around North America.  Your mini vacation certificate is valid for 2 adults. "+
						   "	Please understand these vacations are not intended for group travel or anyone currently working in the travel industry.  You will have 18 months from today’s date to use your first vacation.  When you return from your first vacation you will have a further 12 months to use your second vacation.  You will then have a further 12 months to use your third vacation after returning from your second."+
						   "	There are no blackout dates as long as you can provide us with at least 45 days advance notice.  If you are planning on traveling in either the months of March or December, please give us at least 60 days advance notice, as that is our high season here in Cancun"+
						   "	You will be responsible for providing your airline tickets, HOTEL TAXES, and a one-time reservation and processing fee of $79 USD, not to be paid until you book your travel dates."+
						   "	You are eligible for this promotion today at a total price of"+ watson.getAmount() +" USD in exchange for 90 minutes of your time previewing the host resorts.";	
		
		
		
		watson.synthesize(greeting,"greeting");
		watson.synthesize(jen,"agent");
		watson.synthesize(rules, "rules");
		watson.synthesize(amount, "amout");
		watson.synthesize(includes, "includes");
		
		
		System.out.println(greeting);
		System.out.println(jen);
		
		
		
		Sox sox = new Sox("");
		//sox.inputFile(inputFile)
		
		
		
		
		
		
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
	public int getLaguna_days() {
		return laguna_days;
	}


	public void setLaguna_days(int laguna_days) {
		this.laguna_days = laguna_days;
	}


	public int getLaguna_nights() {
		return laguna_nights;
	}


	public void setLaguna_nights(int laguna_nights) {
		this.laguna_nights = laguna_nights;
	}


	public int getHtr_days() {
		return htr_days;
	}


	public void setHtr_days(int htr_days) {
		this.htr_days = htr_days;
	}


	public int getHtr_nigths() {
		return htr_nigths;
	}


	public void setHtr_nigths(int htr_nigths) {
		this.htr_nigths = htr_nigths;
	}

	
}
