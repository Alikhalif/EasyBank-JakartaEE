package org.youcode.easybankjakartaee.dao.daoImp;

import org.youcode.easybankjakartaee.connection.DB;
import org.youcode.easybankjakartaee.dao.ClientDAO;
import org.youcode.easybankjakartaee.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAOImpl implements ClientDAO {
    private final Connection conn;

    public ClientDAOImpl() {
        conn = DB.getInstance().getConnection();
    }

    public ClientDAOImpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Optional<Client> create(Client client) {
        String insertSQL = "INSERT INTO clients (firstName, lastName, birthDate, phone, address) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING code";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(client.getBirthDate()));
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setString(5, client.getAddress());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                //throw new ClientException("Creating client failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int code = generatedKeys.getInt(1);
                    client.setCode(code);
                } else {
                    //throw new ClientException("Creating client failed, no ID obtained.");
                }
            }

            return Optional.of(client);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public Optional<Client> update(Client client) {
        String updateSQL = "UPDATE clients " +
                "SET firstName = ?, lastName = ?, birthDate = ?, phone = ?, address = ? " +
                "WHERE code = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(client.getBirthDate()));
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setString(5, client.getAddress());
            preparedStatement.setInt(6, client.getCode());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                //throw new ClientException("Updating client failed, no rows affected.");
            }

            return Optional.of(client);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Integer code) {
        String deleteSQL = "DELETE FROM clients WHERE code = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, code);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Client> findByID(Integer code) {
        String selectSQL = "SELECT * FROM clients WHERE code = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = new Client();
                    client.setCode(resultSet.getInt("code"));
                    client.setFirstName(resultSet.getString("firstName"));
                    client.setLastName(resultSet.getString("lastName"));
                    client.setBirthDate(resultSet.getDate("birthDate").toLocalDate());
                    client.setPhone(resultSet.getString("phone"));
                    client.setAddress(resultSet.getString("address"));

                    return Optional.of(client);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM clients";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {

            while (resultSet.next()) {
                Client client = new Client();
                client.setCode(resultSet.getInt("code"));
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                client.setBirthDate(resultSet.getDate("birthDate").toLocalDate());
                client.setPhone(resultSet.getString("phone"));
                client.setAddress(resultSet.getString("address"));

                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public List<Client> findByAttribute(String searchValue) {
        List<Client> clients = new ArrayList<>();

        String selectByAttributeSQL = "SELECT * FROM clients WHERE " +
                "firstName LIKE ? OR " +
                "lastName LIKE ? OR " +
                "birthDate::TEXT LIKE ? OR " +
                "phone LIKE ? OR " +
                "address LIKE ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectByAttributeSQL)) {
            String wildcardSearchValue = "%" + searchValue + "%";
            for (int i = 1; i <= 5; i++) {
                preparedStatement.setString(i, wildcardSearchValue);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Client client = new Client();
                    client.setCode(resultSet.getInt("code"));
                    client.setFirstName(resultSet.getString("firstName"));
                    client.setLastName(resultSet.getString("lastName"));
                    client.setBirthDate(resultSet.getDate("birthDate").toLocalDate());
                    client.setPhone(resultSet.getString("phone"));
                    client.setAddress(resultSet.getString("address"));

                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            //throw new ClientException("Error searching for clients by attribute.");
        }

        return clients;
    }
}
