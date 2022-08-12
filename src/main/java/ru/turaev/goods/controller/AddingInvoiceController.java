package ru.turaev.goods.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.turaev.goods.model.AddingInvoice;
import ru.turaev.goods.service.AddingInvoiceService;

@RestController
@RequestMapping("api/v1/adding-invoices")
@RequiredArgsConstructor
public class AddingInvoiceController {
    private final AddingInvoiceService addingInvoiceService;

    @PostMapping
    public AddingInvoice addAddingInvoice(@RequestBody AddingInvoice addingInvoice) {
        return addingInvoiceService.save(addingInvoice);
    }
}
