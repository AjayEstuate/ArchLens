package com.archlens;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class test3 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter configuration name: ");
		String configName = scanner.nextLine();

		System.out.print("Enter hostname: ");
		String hostname = scanner.nextLine();

		System.out.print("Enter port: ");
		int port = Integer.parseInt(scanner.nextLine());

		System.out.print("Enter username: ");
		String username = scanner.nextLine();

		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		scanner.close();

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode configNode = objectMapper.createObjectNode();
		ObjectNode connectionNode = objectMapper.createObjectNode();

		connectionNode.put("connectionURL", "jdbc:hive2://" + hostname + ":" + port);
		connectionNode.put("username", username);
		connectionNode.put("password", password);

		configNode.set(configName, connectionNode);

		try {
			FileWriter fileWriter = new FileWriter("property.json");
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, configNode);
			fileWriter.close();
			System.out.println("Configuration has been written to config.json successfully!");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error while writing the configuration to config.json");
		}
	}
}


