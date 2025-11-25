package com.mobydrake.billing.service;

import com.mobydrake.billing.data.InvoiceAdjustEvent;
import com.mobydrake.billing.data.InvoiceConfirmationEvent;
import com.mobydrake.billing.data.InvoiceEvent;
import com.mobydrake.billing.mapper.InvoiceAdjustMapper;
import com.mobydrake.billing.mapper.InvoiceMapper;
import com.mobydrake.billing.model.Invoice;
import com.mobydrake.billing.model.InvoiceAdjust;
import com.mobydrake.billing.repository.InvoiceAdjustRepository;
import com.mobydrake.billing.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@EnableRabbit
@RequiredArgsConstructor
public class PaymentService {

    private static final Random random = new Random();

    private final InvoiceMapper invoiceMapper;
    private final InvoiceAdjustMapper adjustMapper;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceAdjustRepository adjustRepository;
    private final NewTopic invoicesConfirmationTopic;
    private final KafkaTemplate<String, InvoiceConfirmationEvent> kafkaTemplate;

    @RabbitListener(queues = "invoices-request")
    public void requestPayment(InvoiceEvent invoiceEvent) {
        Invoice invoice = invoiceMapper.toEntity(invoiceEvent);
        payment(invoice.getReservation().userId(), invoice.getTotalPrice(), invoiceEvent);
        invoice.setPaid(true);
        invoiceRepository.save(invoice);
        log.info("Invoice {} is paid.", invoice.getId());
        sendConfirmation(invoice);
    }

    @KafkaListener(groupId = "adjusts", topics = "invoices-adjust")
    public void requestAdjustment(InvoiceAdjustEvent adjustEvent) {
        log.info("Received invoice adjustment: {}", adjustEvent);
        InvoiceAdjust invoiceAdjust = adjustMapper.toEntity(adjustEvent);
        payment(invoiceAdjust.getUserId(), invoiceAdjust.getPrice(), invoiceAdjust);
        invoiceAdjust.setPaid(true);
        adjustRepository.save(invoiceAdjust);
        log.info("Invoice adjustment {} is paid.", invoiceAdjust);
    }

    private void sendConfirmation(Invoice invoice) {
        InvoiceConfirmationEvent confirmationEvent = invoiceMapper.toConfirmationEvent(invoice);
        kafkaTemplate.send(invoicesConfirmationTopic.name(), confirmationEvent);
    }

    private void payment(String user, double price, Object data) {
        log.info("Request for payment user: {}, price: {}, data: {}", user, price, data);
        try {
            Thread.sleep(random.nextInt(1000, 5000));
        } catch (InterruptedException exception) {
            log.error("Sleep interrupted.", exception);
        }
    }
}
