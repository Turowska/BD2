package controller;

import model.*;

import java.sql.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseController {

    private static final String url = "jdbc:mysql://localhost:8889/deliveryEarn";
    private static final String user = "bd2_projekt";
    private static final String password = "BD2haslo";

    public static void initialize() throws IOException {
        Path fileName = Path.of("./src/res/tableCreation.txt");
        String InitialCreation = Files.readString(fileName);
        System.out.println(InitialCreation);

        try
        {
            Connection conMySql = DriverManager.getConnection("jdbc:mysql://pma.janowski.xyz:3306/bd2_projekt?allowMultiQueries=true", user, password);

            Statement st2;
            st2 = conMySql.createStatement();
            st2.executeUpdate(InitialCreation);

        }
        catch(SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://pma.janowski.xyz:3306/bd2_projekt?allowMultiQueries=true", user, password);
    }

    void insertIntoDaneKlienta(String name, String lastName, int phoneNumber){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO dane_klienta VALUES(default,?,?,?)");
            insert.setString(1, name);
            insert.setString(2,  lastName);
            insert.setInt(3, phoneNumber);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoStanowisko(String position){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO stanowisko VALUES(default,?)");
            insert.setString(1, position);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoPracownik(String name, String lastName, int positionId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO pracownik VALUES(default,?,?,?)");
            insert.setString(1, name);
            insert.setString(2, lastName);
            insert.setInt(3, positionId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    int insertIntoTransport(Date date, int employeeId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO transport VALUES(default,?,?)", Statement.RETURN_GENERATED_KEYS);
            insert.setDate(1, new java.sql.Date(date.getTime()));
            insert.setInt(2, employeeId);
            insert.execute();
            ResultSet keys = insert.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return -1;
    }

    int insertIntoTransakcja(Date date, double value, int employeeId, Transakcja.transactionType type){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO transakcja VALUES(default,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insert.setDate(1, new java.sql.Date(date.getTime()));
            insert.setDouble(2, value);
            insert.setInt(3, employeeId);
            insert.setString(4, type.name());
            insert.execute();
            ResultSet keys = insert.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return -1;
    }

    void insertIntoHurtownia(String name, String contact){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO hurtownia VALUES(default,?,?)");
            insert.setString(1, name);
            insert.setString(2, contact);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoFaktura(String invoiceNr, Date dueDate, boolean ifPayed, String name, String address, String NIP, int transactionId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO faktura VALUES(default,?,?,?,?,?,?,?)");
            insert.setString(1, invoiceNr);
            insert.setDate(2, new java.sql.Date(dueDate.getTime()));
            insert.setBoolean(3,ifPayed);
            insert.setString(4, name);
            insert.setString(5, address);
            insert.setString(6,NIP);
            insert.setInt(7, transactionId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    int insertIntoDostawa( Date dueDate, double value, int warehouseId, int employeeId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO dostawa VALUES(default,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            insert.setDate(1,new java.sql.Date(dueDate.getTime()));
            insert.setDouble(2, value);
            insert.setInt(3, warehouseId);
            insert.setInt(4, employeeId);
            insert.executeUpdate();
            ResultSet keys = insert.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return -1;
    }

    void insertIntoZamowienie(boolean ifCompleted, Date orderDate, Date realisationDate, int clientId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO zamowienie VALUES(default,?,?,?,?)");
            insert.setBoolean(1,ifCompleted);
            insert.setDate(2, new java.sql.Date(orderDate.getTime()));
            insert.setDate(3, new java.sql.Date(realisationDate.getTime()));
            insert.setInt(4, clientId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoKategoria(String category){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO kategoria VALUES(default,?)");
            insert.setString(1, category);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoProdukt(String name,double cost, int categoryId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO produkt VALUES(default,?,?,?)");
            insert.setString(1, name);
            insert.setDouble(2, cost);
            insert.setInt(3, categoryId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoProduktHurtownia(int productId, int warehouseId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO produkt_hurtownia VALUES(?,?)");
            insert.setInt(1, productId);
            insert.setInt(2, warehouseId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoMagazyn(int quantity, int shelfId, int alleyId, int productId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO magazyn VALUES(?,?,?,?)");
            insert.setInt(1, quantity);
            insert.setInt(2, shelfId);
            insert.setInt(3, alleyId);
            insert.setInt(4, productId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoSklep(int quantity, int productId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO sklep VALUES(?,?)");
            insert.setInt(1, quantity);
            insert.setInt(2, productId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoPozycjaDostawa(int quantity, int deliveryId, int productId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO pozycja_dostawa VALUES(?,?,?)");
            insert.setInt(1, quantity);
            insert.setInt(2, deliveryId);
            insert.setInt(3, productId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoPozycjaParagon(int quantity, double cost, int transactionId, int productId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO pozycja_paragon VALUES(?,?,?,?)");
            insert.setInt(1, quantity);
            insert.setDouble(2, cost);
            insert.setInt(3, transactionId);
            insert.setInt(4, productId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoPozycjaTransport(int quantity, int transportId, int productId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO pozycja_transport VALUES(?,?,?)");
            insert.setInt(1, quantity);
            insert.setInt(2, transportId);
            insert.setInt(3, productId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    void insertIntoPozycjaZamówienie(int quantity, double cost, int orderId, int productId){
        try{
            Connection conn = getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO pozycja_zamowienie VALUES(?,?,?,?)");
            insert.setInt(1, quantity);
            insert.setDouble(2,cost);
            insert.setInt(3, orderId);
            insert.setInt(4, productId);
            insert.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    List<Stanowisko> selectAllFromStanowisko(){
        List<Stanowisko> positions = new ArrayList<>();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from stanowisko");

            while (rs.next()) {
                positions.add(new Stanowisko(rs.getInt("id_stanowisko"), rs.getString("nazwa")));
            }
            st.close();
        } catch(SQLException ex){
           ex.printStackTrace();
        }
        return positions;
    }

    List<Pracownik> selectAllFromPracownik(){
        List<Pracownik> employees = new ArrayList<>();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from pracownik");

            while (rs.next()) {
                employees.add(new Pracownik(rs.getInt("id_pracownik"), rs.getString("imie"),
                        rs.getString("nazwisko"), rs.getInt("stanowisko_id_stanowisko")));
            }
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return employees;
    }

    List<Transakcja> selectAllFromTransakcja(){
        List<Transakcja> transactions = new ArrayList<>();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from transakcja");

            while (rs.next()) {
                transactions.add(new Transakcja(rs.getInt("id_transakcja"), rs.getDate("data"),
                        rs.getDouble("kwota"), rs.getInt("pracownik_id_pracownik"),
                        Transakcja.transactionType.valueOf(rs.getString("typ"))));
            }
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return transactions;
    }

    List<Transakcja> selectFakturaOnlyFromTransakcja(){
        List<Transakcja> transactions = new ArrayList<>();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from transakcja WHERE typ ='FAKTURA'");

            while (rs.next()) {
                transactions.add(new Transakcja(rs.getInt("id_transakcja"), rs.getDate("data"),
                        rs.getDouble("kwota"), rs.getInt("pracownik_id_pracownik"),
                        Transakcja.transactionType.valueOf(rs.getString("typ"))));
            }
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return transactions;
    }

    List<Hurtownia> selectAllFromHurtownia(){
        List<Hurtownia> warehouses = new ArrayList<>();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from hurtownia");

            while (rs.next()) {
                warehouses.add(new Hurtownia(rs.getInt("id_hurtownia"), rs.getString("nazwa"),
                        rs.getString("kontakt")));
            }
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return warehouses;
    }

    List<DaneKlienta> selectAllFromDaneKlienta(){
        List<DaneKlienta> clients = new ArrayList<>();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from dane_klienta");

            while (rs.next()) {
                clients.add(new DaneKlienta(rs.getInt("id_klient"), rs.getString("imie"),
                        rs.getString("nazwisko"),String.valueOf(rs.getInt("telefon_kontaktowy"))));
            }
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return clients;
    }

    List<Kategoria> selectAllFromKategoria(){
        List<Kategoria> categories = new ArrayList<>();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from kategoria");

            while (rs.next()) {
                categories.add(new Kategoria(rs.getInt("id_kategoria"),
                        Kategoria.category.valueOf(rs.getString("nazwa"))));
            }
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return categories;
    }
    List<Produkt> selectAllFromProdukt(){
        List<Produkt> products = new ArrayList<>();
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from produkt");

            while (rs.next()) {
                products.add(new Produkt(rs.getInt("id_produkt"),
                        rs.getString("nazwa"), rs.getDouble("koszt"),
                        rs.getInt("kategoria_id_kategoria")));
            }
            st.close();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return products;
    }
}