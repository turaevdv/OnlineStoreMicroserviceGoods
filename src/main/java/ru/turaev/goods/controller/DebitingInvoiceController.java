package ru.turaev.goods.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.turaev.goods.dto.DebitingInvoiceDTO;
import ru.turaev.goods.model.DebitingInvoice;
import ru.turaev.goods.service.DebitingInvoiceService;

import java.util.List;

@RestController
@RequestMapping("api/v1/debiting-invoices")
@RequiredArgsConstructor
public class DebitingInvoiceController {
    private final DebitingInvoiceService debitingInvoiceService;

    @GetMapping("/confirmations")
    public List<DebitingInvoice> getUnconfirmedInvoices() {
        return debitingInvoiceService.getAllUnconfirmedInvoices();
    }

    @GetMapping("/confirmations/{id}")
    public DebitingInvoice getUnconfirmedInvoicesById(@PathVariable long id) {
        return debitingInvoiceService.findUnconfirmedInvoiceById(id);
    }

    @GetMapping("/{id}")
    public DebitingInvoice getInvoicesById(@PathVariable long id) {
        return debitingInvoiceService.findById(id);
    }

    @PostMapping
    public DebitingInvoice addDebitingInvoice(@RequestBody DebitingInvoiceDTO debitingInvoiceDTO) {
        return debitingInvoiceService.add(debitingInvoiceDTO);
    }

    @PutMapping("/confirmations/{id}")
    public DebitingInvoice confirmDebitingInvoice(@PathVariable long id) {
        return debitingInvoiceService.confirmInvoice(id);
    }

    @PutMapping("/confirmations")
    public List<DebitingInvoice> confirmAllDebitingInvoices() {
        return debitingInvoiceService.confirmAllInvoices();
    }

    @DeleteMapping("/{id}")
    public DebitingInvoice deleteDebitingInvoice(@PathVariable long id) {
        return debitingInvoiceService.deleteInvoice(id);
    }
}
