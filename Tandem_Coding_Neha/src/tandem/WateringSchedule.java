package tandem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WateringSchedule {

	public static void main(String[] args) throws FileNotFoundException{
		String filePath = "C:\\Users\\nehab\\Documents\\Companies\\Tandem_Coding_Neha\\src\\Apprentice_WeGrowInTandem_Data.json";
		
		int days = 84;                     //12 weeks starting monday(7*12)
		List<PlantDetails> plantDet = new ArrayList<PlantDetails>();    // to store the JSON data
		List<List<String>> dayPlant = new ArrayList<>(Collections.nCopies(days, null));  //stores the plants to be watered

		plantDet = readPlantDetails(filePath,plantDet);
		if(plantDet.size()==0)
			return;
		createWaterSchedule(plantDet,dayPlant,days);
		displayWateringSchedule(dayPlant);
		
	}

	private static void displayWateringSchedule(List<List<String>> dayPlant) {
		
		DaysOfWeek dw= new DaysOfWeek();
		System.out.println("View watering schedule for next 12 weeks for all the plants");
		System.out.println("------------------------------------------------------------");
		while(true)
		{
			try {
				Scanner input = new Scanner(System.in);
		    	
		    	System.out.print("Enter a day between 1-84 for watering schedule: ");
		    	int number=0;

	    		String exit = input.nextLine();
	    		if(exit.equals("exit"))
	    			break;
	    		else
	    		{	
		    		try {
			    	    number = Integer.parseInt(exit);
			    	    	
			    	}catch(Exception e)
			    	{
			    		System.out.println("Invalid input entered. Please enter digits only");
			    		continue;
			    	}    	

	    		}
		    	if(number>0 && number<85)
		    	{
		    		System.out.println("Day: " + number + " " +dw.getDay(number%7));
		    		if(dayPlant.get(number-1) != null)
		    			System.out.println(dayPlant.get(number-1));
		    		else
		    			System.out.println("No Plants to water today");
		    	}
		    	else
		    	{
		    		System.out.println("Your input is not in specified range");
		    	}
				
			}
			catch(Exception e)
			{
				
			}
		}
		
		System.out.println("------------------Program Execution Ended------------------");
		
	}

	private static void createWaterSchedule(List<PlantDetails> plantDet, List<List<String>> dayPlant, int days) {
	
		int wateringDay = 1;
		
		//Updated 84 days with respective plants to be watered		
		for(int i=0;i<plantDet.size();i++)
		{
			String[] daysString = plantDet.get(i).getWater_after().split(" ");
			int frequency = Integer.parseInt(daysString[0]);
			wateringDay=1;
			
			while(wateringDay<=days) {
				
				if(dayPlant.get(wateringDay-1)==null)
				{
					dayPlant.set(wateringDay-1, new ArrayList<String>());
				}
				dayPlant.get(wateringDay-1).add(plantDet.get(i).getName());   //used 0-based indexing, day 1 gets stored at index 0
				wateringDay += frequency;
				if(wateringDay%7==6)  					// if day is Saturday, water on Friday
				{
					wateringDay += -1;					
				}
				if(wateringDay%7==0)  					// if day is Sunday, water on Monday
				{
					wateringDay += 1;					
				}
			}
		}
		
		
	}

	private static List<PlantDetails> readPlantDetails(String filePath, List<PlantDetails> plantDet) {
		
		JSONArray jsonData =null;
		try {
			jsonData = (JSONArray) readJson(filePath);
		} catch (FileNotFoundException e) {
			System.out.println("File not found at specified location "+filePath);
			return plantDet;
		}
		 if(jsonData!=null)
		 {
			 for (int i=0;i<jsonData.size();i++){ 
				 PlantDetails pd = new PlantDetails();
				 pd.setName((String)((JSONObject)jsonData.get(i)).get("name"));
				 pd.setWater_after((String)((JSONObject)jsonData.get(i)).get("water_after"));
				 plantDet.add(pd);
			 }
		 }
		 

		return plantDet;	
	}

	private static Object readJson(String filePath) throws FileNotFoundException {	
	    try {
	    	FileReader reader = new FileReader(filePath);
		    JSONParser jsonParser = new JSONParser();
			return jsonParser.parse(reader);
		} catch (IOException | ParseException e) {
			throw new FileNotFoundException();
		}
	}

}
