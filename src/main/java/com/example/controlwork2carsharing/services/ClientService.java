package com.example.controlwork2carsharing.services;

import com.example.controlwork2carsharing.entities.Client;
import com.example.controlwork2carsharing.repositories.ClientRepository;
import com.example.controlwork2carsharing.validators.EntityValidator;
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

    public Message saveClient(Client client){
        Message result = new EntityValidator().validateClient(client);

        if (result.getWebclass().equals("alert alert-success")) {
            if (client.getId() == null) {
                result.setText("Client was added successful");
                clientRepository.save(client);
            } else if (this.findClientByID(client.getId()).isEmpty()) {
                result = new Message("No such clients in the list", "alert alert-warning");
            } else {
                result.setText("Client was updated successful");
                clientRepository.save(client);
            }
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
