package org.example;
import org.example.TablePrinter.Table;
import org.example.TablePrinter.TableHeader;
import org.example.TablePrinter.TableLiner;
import org.example.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        database(session, transaction);

    }
    public static void getData(Scanner Sc, Session s, Transaction t){
        System.out.println();

        TableHeader.print(8, "id", 16, "city", 26, "email", 14, "name", 20, "password");
        long i=1;
        while(i!=0){
           User user = s.get(User.class, i);
           if(user == null) break;
           Table.print(8, user.getId(), 16, user.getCity(), 26, user.getEmail(), 14, user.getName(), 20, user.getCity());
           i++;
        }
        TableLiner.print(8, 16, 26, 14, 20);
        System.out.println();
        System.out.println();
    }
    public static void deleteData(Scanner Sc, Session s, Transaction t){
        System.out.println();
        System.out.print("Enter id: ");
        long id = Sc.nextLong();
        User user = s.get(User.class, id);
        try{
            s.remove(user);
            t.commit();
        }catch (Exception e){
            t.rollback();
            System.out.println(e.getMessage());
        }
    }
    public static void insertData(Scanner Sc, Session s, Transaction t){
        System.out.println();
        System.out.print("Enter id: ");
        long id = Sc.nextLong();
        System.out.print("Enter name: ");
        Sc.nextLine();
        String name = Sc.nextLine();
        System.out.print("Enter email: ");
        String email = Sc.nextLine();
        System.out.print("Enter password: ");
        String password = Sc.nextLine();
        System.out.print("Enter city: ");
        String city = Sc.nextLine();

        User user = new User(id, name, email, password, city);

        try{
            s.persist(user);
            t.commit();
        }catch (Exception e){
            t.rollback();
            System.out.println(e.getMessage());
        }
    }
    public static void updateData(Scanner Sc ,Session s, Transaction t){
        System.out.println();
        System.out.print("Enter id: ");
        long id = Sc.nextLong();
        System.out.print("Enter name: ");
        Sc.nextLine();
        String name = Sc.nextLine();
        System.out.print("Enter email: ");
        String email = Sc.nextLine();
        System.out.print("Enter password: ");
        String password = Sc.nextLine();
        System.out.print("Enter city: ");
        String city = Sc.nextLine();

        User user = s.get(User.class, id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setCity(city);
        try{
            s.merge(user);
            t.commit();
        }catch (Exception e){
            t.rollback();
            System.out.println(e.getMessage());
        }

    }

    public static void database(Session s, Transaction t){
        System.out.println();
        Scanner Sc = new Scanner(System.in);

        int option = -1;
        while(option!=0){
            System.out.println("1. Insert Data");
            System.out.println("2. Update Data");
            System.out.println("3. Delete Data");
            System.out.println("4. Print Data");
            System.out.println("To Exit Press 0");
            System.out.print("Choose an Option: ");
            option = Sc.nextInt();
            if(option == 0) break;

            switch(option){
                case 1:
                    insertData(Sc, s, t);
                    break;
                case 2:
                    updateData(Sc, s, t);
                    break;
                case 3:
                    deleteData(Sc, s, t);
                    break;
                case 4:
                    getData(Sc, s, t);
                    break;
                default:
                    System.out.println("Invalid Option!!");
                    break;
            }

        }
    }
}