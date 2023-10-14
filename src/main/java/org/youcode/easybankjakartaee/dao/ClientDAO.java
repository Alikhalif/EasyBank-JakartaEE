package org.youcode.easybankjakartaee.dao;

import org.youcode.easybankjakartaee.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDAO {
    Optional<Client> create(Client client);
    Optional<Client> update(Client client);
    boolean delete(Integer code);
    Optional<Client> findByID(Integer code);
    List<Client> getAll();
    List<Client> findByAttribute(String searchValue);
}
