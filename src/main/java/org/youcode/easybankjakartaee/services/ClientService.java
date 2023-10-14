package org.youcode.easybankjakartaee.services;

import org.youcode.easybankjakartaee.dao.ClientDAO;
import org.youcode.easybankjakartaee.entities.Client;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientDAO clientDao;
    public ClientService(ClientDAO clientDao) {
        this.clientDao = clientDao;
    }

    public Optional<Client> addClient(Client client) {
        Optional<Client> optionalClient = clientDao.create(client);
        if (optionalClient.isPresent()) {
            return optionalClient;
        } else {
            return Optional.empty();
        }
    }

    public Boolean deleteClient(Integer code) {
        Boolean res = clientDao.delete(code);
        return res;
    }

    public Optional<Client> updateClient(Client client) {
        return clientDao.update(client);
    }

    public Optional<Client> getClient(Integer code) {
        Optional<Client> optionalClient = clientDao.findByID(code);
        if (optionalClient.isPresent()) {
            return optionalClient;
        } else {
            return Optional.empty();
        }
    }

    public List<Client> getClients() {
        List<Client> clients = clientDao.getAll();
        return clients;
    }
}
