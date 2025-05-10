package wheather;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Wheatherapp {
	private static JFrame frame;
	private static JTextField field;
	private static JButton button;
	private static JTextArea weatherdisplay;
	private static String apikey = "3329ba0407a7da70d4109475a5195f19";
	
	public static void main(String[] args) {
		frame = new JFrame(" Whethee Forecasting App ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		frame.setLayout(new FlowLayout());
		
		field = new JTextField(25);
		button = new JButton(" Fetch Wether ");
		weatherdisplay = new JTextArea(10,30);
		weatherdisplay.setEditable(false);
		frame.add(new JLabel(" Enter the City name : "));
		frame.add(field);
		frame.add(button);
		frame.add(weatherdisplay);
		frame.setVisible(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String city = field.getText();
				String information = fetchWeatherinfo(city);
				weatherdisplay.setText(information); 
			}
		}
				
				);
	}
	protected static String fetchWeatherinfo(String city) {
		try {
			String urlString = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric\r\n"
					+ "",city,apikey);
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader read = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder res = new StringBuilder();
			String inputLine;
			while((inputLine=read.readLine())!=null) {
				res.append(inputLine);
			}
			read.close();
			JSONObject object = new JSONObject(res.toString());
			JSONObject main = object.getJSONObject("main");
			double temparature = main.getDouble("temp");
			long humidity = main.getLong("humidity");
			double temperatureCelsius = temparature - 273.15;
			JSONArray array = object.getJSONArray("weather");
			JSONObject weather = array.getJSONObject(0);
			String descripiton = weather.getString("description");
			return String.format(" descripiton: %s\n Temparature: %.2f\n Humidity: %d%%",descripiton,temperatureCelsius,humidity);
		}catch(Exception o) {
			return " please enter the correct city ";
		}
	}

}
