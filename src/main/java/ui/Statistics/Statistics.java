package ui.Statistics;

import entities.dbEntities.User;
import service.ServiceException;
import service.SimpleService.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Statistics {

    private User user;
    private Service service;
    private Scanner scanner;
    private String key;
    private boolean flag = true;

    public Statistics(User user, Service service){
        this.user = user;
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void getStatistics(){
        while(flag) {
            System.out.println();
            System.out.println("------------------- STATISTICS -----------------");
            System.out.println("LEGEND:");
            System.out.println("ENTER 'D;d', where 'D' is the key and 'd' is the date to see the statistics of that date");
            System.out.println("ENTER 'FD;d', where 'FD' is the key and 'd' is the date to see the statistics from that date");
            System.out.println("PRESS 'G' to see global statistics");
            System.out.println("PRESS 'Q' to quit");
            System.out.println("UASGE EXAMPLE: FD ; 2018-04-09");
            System.out.print("Your input: ");

            key = scanner.nextLine();

            if (key.equals("Q") || key.equals("q")) {
                flag = false;
                break;
            }

            String internalKey = "";
            String date = "";
            String[] parts = key.split(";");
            if (parts.length >= 2) {
                internalKey = parts[0].trim();
                date = parts[1].trim();
            } else {
                internalKey = key;
            }


            if (internalKey.equals("G") || internalKey.equals("g")){
                try {
                    System.out.println("GLOBAL STATISTICS: ");
                    int total = 0;
                    HashMap<String, Integer> map = service.getNumberOfVulnerabilitiesBy();
                    Iterator iterator = map.entrySet().iterator();
                    while(iterator.hasNext()){
                        Map.Entry pair = (Map.Entry)iterator.next();
                        System.out.println("From source type: " + pair.getKey() + " we aggregated " + pair.getValue() + "" +
                                " number of Vulnerabilities");
                        total += Integer.parseInt(pair.getValue().toString());
                    }

                    System.out.println("Total number of aggregated vulnerabilities: " + total);

                } catch(ServiceException e){
                    e.printStackTrace();
                    System.out.println("Your search caused exception.");
                }
            }

            if (internalKey.equals("D") || internalKey.equals("d")){
                try {
                    System.out.println("STATISTICS AT DATE " + date);
                    int total = 0;
                    HashMap<String, Integer> map = service.getNumberOfVulnerabilitiesByDateOne(date);
                    Iterator iterator = map.entrySet().iterator();
                    while(iterator.hasNext()){
                        Map.Entry pair = (Map.Entry)iterator.next();
                        System.out.println("From source type: " + pair.getKey() + " we aggregated " + pair.getValue() + "" +
                                " number of Vulnerabilities");
                        total += Integer.parseInt(pair.getValue().toString());
                    }

                    System.out.println("Total number of aggregated vulnerabilities: " + total);

                } catch(ServiceException e){
                    e.printStackTrace();
                    System.out.println("Your search caused exception.");
                }
            }

            if (internalKey.equals("FD") || internalKey.equals("fd")){
                try {
                    System.out.println("STATISTICS FROM DATE " + date);
                    int total = 0;
                    HashMap<String, Integer> map = service.getNumberOfVulnerabilitiesFromDate(date);
                    Iterator iterator = map.entrySet().iterator();
                    while(iterator.hasNext()){
                        Map.Entry pair = (Map.Entry)iterator.next();
                        System.out.println("From source type: " + pair.getKey() + " we aggregated " + pair.getValue() + "" +
                                " number of Vulnerabilities");
                        total += Integer.parseInt(pair.getValue().toString());
                    }

                    System.out.println("Total number of aggregated vulnerabilities: " + total);

                } catch(ServiceException e){
                    e.printStackTrace();
                    System.out.println("Your search caused exception.");
                }
            }
        }
    }
}
