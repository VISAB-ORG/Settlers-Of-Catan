package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.gson.Gson;

import cbr_util.CBREngine;
import model.MapRequest;
import model.MapResponse;
import model.Request;
import model.Response;
import model.Situation;
import model.plan.Plan;

/**
 * 
 * Diese Klasse startet den Server und wird vom Spiel angesteurt, wenn mit KIs gespielt werden soll. 
 * 
 * @author Tjark Harjes
 *
 */
public class CBRSystem {
	
	//private static JTextArea jtf;
	private static PlayerManager manager;

	/**
	 * Singleton f¸r den Zugriff auf das CBR-System
	 */
	private static CBREngine engine;

	/**
	 * Diese Methode stellt den zentralen Punkt des CBR-System dar. Hier wird
	 * die Verbindung vom C#-Projekt akzeptiert, die Daten in Empfang genommen
	 * und Anworten werden wiedrum abgeschickt
	 * 
	 * @param port
	 *            
	 */
	public static void receive(int port) {
		while (true) {

			ServerSocket serverSocket = null;

			Socket socket = null;
			InputStream in = null;
			OutputStream out = null;
			//System.out.println("Hallo");

			try {
				
				//System.out.println("Warum funktionierst du nicht?");
				//jtf.setText(jtf.getText() + "\n Warum funktionierst du nicht? ");
				serverSocket = new ServerSocket(port);
				//System.out.println("Ich weiﬂ nicht, was das soll!");
				//jtf.setText(jtf.getText() + "\n Ich weiﬂ nicht, was das soll!");

				socket = serverSocket.accept();
				//System.out.println("Ich weiﬂ nicht, was das soll!");
				//jtf.setText(jtf.getText() + "\n Ich weiﬂ nicht, was das soll2!");
				in = socket.getInputStream();
				//System.out.println("Ich weiﬂ nicht, was das soll!");
				out = socket.getOutputStream();
				//System.out.println("Ich weiﬂ nicht, was das soll!");
				String clientSentence;

				//System.out.println("Ich weiﬂ nicht, was das soll!");
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(in));
				clientSentence = inFromClient.readLine();
				//System.out.println("clientSentence: " + clientSentence);
				//jtf.setText(jtf.getText() + "\n clientSentence: " + clientSentence);
				Gson gson = new Gson();

				Request request = gson.fromJson(clientSentence, Request.class);
				System.out.println("Received in java: " + request.toString());
				//jtf.setText(jtf.getText() + "\n Received in java: " + request.toString());
				
				//Response response = manager.answerRequest(request);
				
				//jtf.setText(jtf.getText() + "\nExecuting retrieval...1");

				String player = request.getSituation().getPlayer();
				//jtf.setText(jtf.getText() + "\nExecuting retrieval...2");
				//Thread.sleep(5000);
				handlePlayerCaseBase(player);
				//jtf.setText(jtf.getText() + "\nExecuting retrieval...3");
				
				Response response = engine.executeRetrieval(request);

				//jtf.setText(jtf.getText() + "\nExecuting retrieval...4");
				String toC = gson.toJson(response);
				//jtf.setText(jtf.getText() + "\n Json: " + toC);

				//jtf.setText(jtf.getText() + "\n***********************CB Size: " + engine.getCaseBaseForPlayer(player).getCases().size() + "*************************");
				
				//writeToFile("Estimated Plan: " + toC);
				System.out.println("Status: " + toC);
				System.out.println("Next Case");

				out.write(toC.toString().concat("\r\n").getBytes());
				out.flush();
				System.out.println("toC sent!");

			} catch (IOException exc) {
				System.out.println(exc.getMessage());
				//writeToFile(exc.getMessage());
				exc.printStackTrace();
				//jtf.setText(jtf.getText() + exc.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				//jtf.setText(jtf.getText() + e.getMessage());
				System.out.println(e.getMessage());
			} finally {
				try {
					//engine.getCbrProject().save();
					out.close();
					in.close();
					socket.close();
					serverSocket.close();
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}


	/**
	 * Die Methode erzeugt die Fallbasis
	 * 
	 * @param name
	 *            
	 */
	private static void handlePlayerCaseBase(String name) {
		
		if (!engine.caseBaseForPlayerAlreadyExists(name)) {
			try {
				//jtf.setText(jtf.getText() + "\nWir erstellen eine CaseBase f¸r Spieler: " +  name);
				engine.createCaseBaseForPlayer(name);
				//jtf.setText(jtf.getText() + "\nCase Base erstellt!");
				//Thread.sleep(5000);
				engine.addDefaultCases(name);
				//jtf.setText(jtf.getText() + "\nCases hinzugef¸gt!");
				//Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
				//jtf.setText(e.getMessage());
			}
		}
	}

	/**
	 * Getter.
	 * 
	 * @return engine
	 */
	public static CBREngine getEngine() {
		return engine;
	}

	/**
	 * Main-Methode
	 */
	public static void main(String[] args) throws FileNotFoundException {
		int port;
		//JFrame jframe = new JFrame();
		//jframe.setVisible(true);
		
		PrintStream out = new PrintStream(new FileOutputStream("Java-Console_Output.txt"));
		System.setOut(out);
		if (args.length == 0) {
			port = 5555;
		} else {
			port = Integer.parseInt(args[0]);
		}
		//jtf = new JTextArea();
		//jtf.setBounds(20, 200, 300, 300);
		//jframe.add(jtf);
		//jframe.setBounds(500, 500, 500, 500);
		System.out.println("Starting server on port " + port);
		//jtf.setText(jtf.getText() + "Starting server on port " + port);
		
		//JScrollPane scroll = new JScrollPane (jtf, 
		//JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//jframe.add(scroll);
		//jframe.setVisible (true);

		engine = CBREngine.getInstance(/*jtf*/);
		//jtf.setText(jtf.getText() + "Starting server on port " + port);
		manager = new PlayerManager(/*jtf*/);
		manager.ChangePlayer(true);
		//jtf.setText(jtf.getText() + "Starting server on port " + port);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					receive(port);
				}
			}
		}).start();
	}
}