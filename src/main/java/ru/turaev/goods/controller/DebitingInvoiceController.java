package ru.turaev.goods.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.turaev.goods.dto.DebitingInvoiceDto;
import ru.turaev.goods.model.DebitingInvoice;
import ru.turaev.goods.service.DebitingInvoiceService;

import java.time.LocalDate;
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
    public DebitingInvoice addDebitingInvoice(@RequestBody DebitingInvoiceDto debitingInvoiceDTO) {
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

    @GetMapping("/pickup-point/{id}/debiting-invoices-by-period")
    public List<DebitingInvoiceDto> getDebitingInvoicesByPeriod(@PathVariable long id,
                                                                @RequestParam("begin") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate begin,
                                                                @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate end) {
        return debitingInvoiceService.getDebitingInvoicesByPeriod(id, begin, end);
    }
}
