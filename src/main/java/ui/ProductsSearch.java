package ui;

import entities.dbEntities.Product;
import entities.dbEntities.User;
import entities.dbEntities.Vulnerability;
import service.SimpleService.Service;

import java.util.List;
import java.util.Scanner;

public class ProductsSearch {

    private User user;
    private Service service;
    private Scanner scanner;
    private String key;
    private boolean flag = true;

    public ProductsSearch(User user, Service service){
        this.user = user;
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void searchByProduct(){
        while(flag) {
            System.out.println();
            System.out.println("------------------- SEARCH VULNERABILITY BY PRODUCT -----------------");
            System.out.println("LEGEND:");
            System.out.println("PRESS 'Q' to quit");
            System.out.println("ENTER the name of the product you want to search in database and year:");
            System.out.println("Entering name is mandatory; entering date is not necessary.");
            System.out.println("USAGE EXAMPLE 1: linux, kernel");
            System.out.println("USAGE EXAMPLE 2: linux, kernel ; 2018");
            System.out.println("USAGE EXAMPLE 3: linux;2018");
            System.out.print("ENTER THE NAME OR 'q' TO QUIT:");
            key = scanner.nextLine();

            if(key.equals("Q") || key.equals("q")){
                flag = false;
                break;
            }

            if (!key.equals("")) {

                String product = "";
                String date = "";
                String[] parts = key.split(";");
                if(parts.length >= 2) {
                    product = parts[0].trim();
                    date = parts[1].trim();
                } else {
                    product = key;
                }


                if(!product.equals("")) {

                    if(!date.equals("")) {
                        try{
                            List<Product> products = this.service.getProductByName(product);
                            System.out.println("Found " + products.size() + " different products which contain specified name");
                            List<Vulnerability> vulnerabilities = this.service.getVulnerabilityByProductNameAndDate(product,date);
                            System.out.println("Found " + vulnerabilities.size() + " different vulnerabilities in the products");
                            System.out.println("Vulnerabilities: ");
                            for(Vulnerability vulnerability : vulnerabilities){
                                System.out.println("ID: " + vulnerability.getId() + " /// NAME: " +
                                        vulnerability.getName() + " ///// CVE: " + vulnerability.getCve());
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                            System.out.println("Your search caused exception. Please be sure that you are searching the right product.");
                        }

                    } else {
                        try {
                            List<Product> products = this.service.getProductByName(product);
                            System.out.println("Found " + products.size() + " different products which contain specified name");
                            List<Vulnerability> vulnerabilities = this.service.getVulnerabilityByProductName(product);
                            System.out.println("Found " + vulnerabilities.size() + " different vulnerabilities in the products");
                            System.out.println("Vulnerabilities: ");
                            for(Vulnerability vulnerability : vulnerabilities){
                                System.out.println("ID: " + vulnerability.getId() + " /// NAME: " +
                                        vulnerability.getName() + " ///// CVE: " + vulnerability.getCve());
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                            System.out.println("Your search caused exception. Please be sure that you are searching the right product.");
                        }
                    }
                } else {
                    System.out.println("No product entered!");
                }
            }

        }
    }
}
