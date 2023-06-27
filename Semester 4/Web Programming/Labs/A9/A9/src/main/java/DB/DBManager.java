package DB;

import Domain.Photo;
import Domain.User;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private Statement stmt;
    private static Connection connection;

    public DBManager() {
        connect();
    }

    public static void connect() {
        if (connection == null) {
            String url = "jdbc:mysql://localhost:3306/votes";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, "root", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        if (connection == null)
            connect();
        return connection;
    }

    public User authenticate(String username, String password) {
        User user = null;
        try {
            String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("username"), resultSet.getString("password"));
            }
            resultSet.close();
            connection = null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public int getNewID() {
        try {
            String sql = "SELECT MAX(id) FROM photo";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            resultSet.next();
            int newID = resultSet.getInt(1);
            newID++;
            return newID;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getUserName(int userID) {
        try {
            String sql = "SELECT name FROM users WHERE id=" + userID;

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<Photo> getAllPhotos() {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        try {
            String sql = "SELECT * FROM photo";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                photos.add(new Photo(
                        resultSet.getInt("id"),
                        resultSet.getString("url"),
                        resultSet.getInt("userID"),
                        resultSet.getInt("votes")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photos;
    }

    public ArrayList<Photo> getUserPhotos(int userID) {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        try {
            String sql = "SELECT * FROM photo WHERE userID=" + userID;

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                photos.add(new Photo(
                        resultSet.getInt("id"),
                        resultSet.getString("url"),
                        resultSet.getInt("userID"),
                        resultSet.getInt("votes")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photos;
    }

    public ArrayList<Photo> getVotePhotos(int userID) {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        try {
            String sql = "SELECT * FROM photo WHERE userID <> " + userID;

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                photos.add(new Photo(
                        resultSet.getInt("id"),
                        resultSet.getString("url"),
                        resultSet.getInt("userID"),
                        resultSet.getInt("votes")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photos;
    }


    public boolean updatePhoto(int photoID, int totalVotes) {
        try {

            String sql = "UPDATE photo SET votes = ? WHERE id = ?";

            PreparedStatement stmt = getConnection().prepareStatement(sql);

            stmt.setInt(1, totalVotes);
            stmt.setInt(2, photoID);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addPhoto(Photo photo) {

        try {
            String sql = "INSERT INTO photo(id,url,userID,votes) VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = getConnection().prepareStatement(sql);

            stmt.setInt(1, photo.getId());
            stmt.setString(2, photo.getUrl());
            stmt.setInt(3, photo.getuserID());
            stmt.setInt(4, photo.getVotes());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Photo> getTopPhotos(int topNr) {
        ArrayList<Photo> photos = new ArrayList<>();
        try {
            String sql = "select * from photo order by votes desc limit " + topNr;

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                photos.add(new Photo(
                        resultSet.getInt("id"),
                        resultSet.getString("url"),
                        resultSet.getInt("userID"),
                        resultSet.getInt("votes")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photos;
    }
}
