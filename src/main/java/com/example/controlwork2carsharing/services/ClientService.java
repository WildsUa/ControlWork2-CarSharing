package com.example.controlwork2carsharing.services;

import com.example.controlwork2carsharing.entities.Client;
import com.example.controlwork2carsharing.repositories.ClientRepository;
import com.example.controlwork2carsharing.webElements.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<Client> findClientByID(int id){
        return clientRepository.findById(id);
    }

    public List<Client> findAllClients(){
        return clientRepository.findAll();
    }
    public List<Client> findFreeClients(){
        return clientRepository.findFreeClients();
    }

    public Message saveClients(Client client){
        Message result;
        if (client.getId() == null) {
            result = new Message("Client was added successful", "alert alert-success");
            clientRepository.save(client);
        } else if (this.findClientByID(client.getId()).isEmpty()){
            result = new Message("No such clients in the list", "alert alert-warning");
        } else {
            result = new Message("Client was updated successful", "alert alert-success");
            clientRepository.save(client);
        }
        return result;
    }

    public Message deleteClient (Client client){
        Message result;
        if (this.findClientByID(client.getId()).isEmpty()){
            result = new Message("No such client in the list", "alert alert-warning");
        } else {
            result = new Message("Client was deleted successful", "alert alert-success");
            clientRepository.delete(client);
        }
        return result;
    }
}
