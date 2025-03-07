package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;

import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;

    private Map<String, String> voucherPaymentData;
    private Map<String, String> bankPaymentData;
    private List<Product> products;
    private Order order1;

    @BeforeEach
    void setUp() {

        this.voucherPaymentData = new HashMap<>();
        this.voucherPaymentData.put("voucherCode", "ESHOP12345678ABC");

        this.bankPaymentData = new HashMap<>();
        this.bankPaymentData.put("bankName", "CommonWealth");
        this.bankPaymentData.put("referenceCode", "REF1234567890123");

        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Kebab Khas Ngawi");
        product1.setProductQuantity(2);
        this.products.add(product1);

        this.order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products,
                1708560000L, "Barber Rusdi");
    }

    @Test
    void testCreateVoucherPayment() {
        Payment payment = new Payment(PaymentMethod.VOUCHER.getValue(), voucherPaymentData, order1);
        when(paymentRepository.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData))
                .thenReturn(payment);

        Payment result = paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);

        assertEquals(PaymentStatus.WAITING.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
    }

    @Test
    void testCreateBankPayment() {
        Payment payment = new Payment(PaymentMethod.BANK.getValue(), bankPaymentData, order1);
        doReturn(payment).when(paymentRepository).addPayment(order1,
                PaymentMethod.BANK.getValue(), bankPaymentData);
        Payment result = paymentService.addPayment(order1,
                PaymentMethod.BANK.getValue(), bankPaymentData);
        verify(paymentRepository, times(1)).addPayment(order1,
                PaymentMethod.BANK.getValue(), bankPaymentData);
        assertEquals(PaymentStatus.WAITING.getValue(), result.getStatus());
    }

    @Test
    void testCreateVoucherInvalid() {
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "INVALIDCODE");
        Payment payment = paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(), invalidVoucherData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order1.getStatus());
    }

    @Test
    void testGetAllPayments() {
        Payment payment1 = new Payment(PaymentMethod.VOUCHER.getValue(), voucherPaymentData, order1);
        Payment payment2 = new Payment(PaymentMethod.BANK.getValue(), bankPaymentData, order1);
        List<Payment> payments = List.of(payment1, payment2);

        when(paymentRepository.getAllPayments()).thenReturn(payments);

        List<Payment> allPayments = paymentService.getAllPayments();
        assertEquals(2, allPayments.size());
        verify(paymentRepository, times(1)).getAllPayments();
    }


    @Test
    void testGetByIdFound() {
        Payment payment = new Payment(PaymentMethod.VOUCHER.getValue(), voucherPaymentData, order1);
        when(paymentRepository.addPayment(eq(order1), eq(PaymentMethod.VOUCHER.getValue()), eq(voucherPaymentData)))
                .thenReturn(payment);

        Payment addedPayment = paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);

        when(paymentRepository.getPayment(addedPayment.getId())).thenReturn(addedPayment);

        Payment retrievedPayment = paymentService.getPayment(addedPayment.getId());
        assertEquals(addedPayment, retrievedPayment);
        verify(paymentRepository, times(1)).getPayment(addedPayment.getId());
    }

    @Test
    void testGetByIdNotFound() {
        doReturn(null).when(paymentRepository).getPayment("invalidId");
        assertNull(paymentService.getPayment("invalidId"));
    }

    @Test
    void testCreateInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.addPayment(order1, "Mandiri", voucherPaymentData));
    }

    @Test
    void testSetStatusSuccessful() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC");
        Payment payment = new Payment(PaymentMethod.VOUCHER.getValue(), paymentData, order1);

        paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order1.getStatus());

        paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order1.getStatus());
    }


    @Test
    void testSetStatusFail() {
        Payment payment1 = paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.setStatus(payment1, "MEOW")
        );
    }
}