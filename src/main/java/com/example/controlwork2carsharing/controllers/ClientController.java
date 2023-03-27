package com.example.controlwork2carsharing.controllers;

import com.example.controlwork2carsharing.entities.Client;
import com.example.controlwork2carsharing.entities.Order;
import com.example.controlwork2carsharing.entities.Payment;
import com.example.controlwork2carsharing.services.ClientService;
import com.example.controlwork2carsharing.services.PaymentService;
import com.example.controlwork2carsharing.webElements.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    private final ClientService clientService;
    private final PaymentService paymentService;
    @Autowired
    public ClientController(ClientService clientService, PaymentService paymentService) {
        this.clientService = clientService;
        this.paymentService = paymentService;
    }


    @GetMapping("/clients")
    public String getClients (Model model){
        List<Client> clients = clientService.findAllClients();

        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/client_orders/{id}")
    public String ordersByClients(@PathVariable("id") int id, Model model) {
        Optional<Client> client = clientService.findClientByID(id);
        if (client.isEmpty()) {
            return "redirect:/clients";
        }
        model.addAttribute("client", client.get());
        model.addAttribute("orders", client.get().getOrders());
        return "orders_by_client";
    }
    @GetMapping("/client_payments/{id}")
    public String paymentByClients(@PathVariable("id") int id, Model model) {
        Optional<Client> client = clientService.findClientByID(id);
        if (client.isEmpty()) {
            return "redirect:/clients";
        }
        model.addAttribute("client", client.get());
        model.addAttribute("payments", client.get().getPayments());
        return "payments_by_client";
    }

    @GetMapping("/client/{id}")
    public String clientPage(@PathVariable("id") int id, Model model) {
        Optional<Client> client = clientService.findClientByID(id);
        double payed;
        double loan;
        double requiredPayment;
        LocalDate currentDate = LocalDate.now();
        List<Payment> payments;


        if (client.isEmpty()) {
            return "redirect:/clients";
        } else {
            payed = client.get().getPayments().stream().mapToDouble(Payment::getPaymentSum).sum();
            loan = client.get().getOrders().stream().mapToDouble(Order::getCost).sum();
            requiredPayment = loan - payed;

            payments = client.get().getPayments().stream().sorted(Comparator.comparing(Payment::getId)).toList();

            model.addAttribute("client", client.get());
            model.addAttribute("payments", payments);
            model.addAttribute("payed", payed);
            model.addAttribute("loan", loan);
            model.addAttribute("required", requiredPayment);
            model.addAttribute("id", client.get().getId());
            model.addAttribute("current_date", currentDate);
            return "client";

        }
    }
    @PostMapping("/add_client")
    public String addClient(@ModelAttribute( value = "clients" ) Client client, RedirectAttributes redirectAttributes){
        Message message = clientService.saveClient(client);

        redirectAttributes.addFlashAttribute("message", message);
        return"redirect:/clients";
    }

    @PostMapping("/add_payment")
    public String addPayment(@RequestParam(name = "client_id_e") int id,
                             @RequestParam(name = "date") LocalDate date,
                             @RequestParam(name = "chequeId") String chequeId,
                             @RequestParam(name = "paymentSum") double paymentSum,
            RedirectAttributes redirectAttributes){

        Payment payment = new Payment();

        payment.setClient(clientService.findClientByID(id).get());
        payment.setDate(date);
        payment.setChequeId(chequeId);
        payment.setPaymentSum(paymentSum);

        Message message = paymentService.savePayment(payment);

        redirectAttributes.addFlashAttribute("message", message);
        return"redirect:/client/" + payment.getClient().getId();
    }
        /*public String addPayment(@ModelAttribute( value = "payments" ) Payment payment, RedirectAttributes redirectAttributes){
        Message message = paymentService.savePayment(payment);

        redirectAttributes.addFlashAttribute("message", message);
        return"redirect:/client/" + payment.getClient().getId();
    }*/

    @PostMapping("/update_client")
    public String updateClient(@ModelAttribute ( value = "client" ) Client client, RedirectAttributes redirectAttributes){
        Message message = clientService.saveClient(client);

        redirectAttributes.addFlashAttribute("message", message);
        return"redirect:/client/" + client.getId();
    }


}
