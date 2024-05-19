import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.println("Enter name of patient: ");
        String name = scanner.next();
        System.out.println("Enter age of patient: ");
        int age = scanner.nextInt();
        System.out.println("Enter gender of patient: ");
        String gender = scanner.next();

        try{
            String query = "INSERT INTO PATIENTS(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient added successfully.");
            }else {
                System.out.println("Failed to add patient :(");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatient(){
        String query = "SELECT * FROM patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patient Details:");
            System.out.println("+--------+--------------------+--------+------------+");
            System.out.println("| ID     | Name               | Age    | Gender     |");
            System.out.println("+--------+--------------------+--------+------------+");

            while (resultSet.next()){
                //first ko java variables; last ko table column name

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-6s | %-18s | %-6s | %-11s|\n", id, name, age, gender);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean checkPatient(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
