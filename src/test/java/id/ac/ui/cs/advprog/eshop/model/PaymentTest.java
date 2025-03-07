package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

public class PaymentTest {
    private Map<String, String> voucherPaymentData;
    private Map<String, String> bankPaymentData;
    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        this.voucherPaymentData = new HashMap<>();
        this.voucherPaymentData.put("voucherCode", "ESHOP12345678ABC");

        this.bankPaymentData = new HashMap<>();
        this.bankPaymentData.put("bankName", "CommonWealth");
        this.bankPaymentData.put("referenceCode", "refcode911");

        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Kebab Khas Ngawi");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sampo Khas Jomokerto");
        product2.setProductQuantity(1);

        this.products.add(product1);
        this.products.add(product2);
        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products,
                1708560000L, "Barber Rusdi");
    }

    @Test
    void testCreatePaymentWithoutPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("VOUCHER", null, this.order);
        });
    }

    @Test
    void testCreateVoucherPaymentWithInvalidPaymentData() {
        Map<String, String> paymentDataWithoutESHOPPrefix = new HashMap<>();
        Map<String, String> paymentDataWithoutEightNumbers = new HashMap<>();
        Map<String, String> paymentDataWithLessThanSixteenCharacters = new HashMap<>();
        Map<String, String> paymentDataWithGreaterThanSixteenCharacters = new HashMap<>();

        paymentDataWithoutESHOPPrefix.put("voucherCode", "1234ABC5678");
        paymentDataWithoutEightNumbers.put("voucherCode", "ESHOP1234ABC");
        paymentDataWithLessThanSixteenCharacters.put("voucherCode", "ESHOP1234ABC567");
        paymentDataWithGreaterThanSixteenCharacters.put("voucherCode", "ESHOP1234ABC56781234");

        Payment paymentWithoutESHOPPrefix = new Payment("VOUCHER",
                paymentDataWithoutESHOPPrefix, this.order);
        Payment paymentWithoutEightNumbers = new Payment("VOUCHER",
                paymentDataWithoutEightNumbers, this.order);
        Payment paymentWithLessThanSixteenCharacters = new Payment("VOUCHER",
                paymentDataWithLessThanSixteenCharacters, this.order);
        Payment paymentWithGreaterThanSixteenCharacters = new Payment("VOUCHER",
                paymentDataWithGreaterThanSixteenCharacters, this.order);

        assertEquals(PaymentStatus.REJECTED.getValue(), paymentWithoutESHOPPrefix.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), paymentWithoutEightNumbers.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), paymentWithLessThanSixteenCharacters.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), paymentWithGreaterThanSixteenCharacters.getStatus());
    }

    @Test
    void testCreateBankPaymentWithInvalidData() {
        Map<String, String> paymentDataWithoutbankName = new HashMap<>();
        Map<String, String> paymentDataWithoutreferenceCode = new HashMap<>();
        Map<String, String> paymentDataWithEmptybankName = new HashMap<>();
        Map<String, String> paymentDataWithEmptyreferenceCode = new HashMap<>();

        paymentDataWithoutbankName.put("referenceCode", "coderef420420");
        paymentDataWithoutreferenceCode.put("bankName", "CommonWealth");
        paymentDataWithEmptybankName.put("bankName", "");
        paymentDataWithEmptybankName.put("referenceCode", "coderef420420");
        paymentDataWithEmptyreferenceCode.put("referenceCode", "");
        paymentDataWithEmptyreferenceCode.put("bankName", "CommonWealth");

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("BANK", paymentDataWithoutbankName, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("BANK", paymentDataWithoutreferenceCode, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("BANK", paymentDataWithEmptybankName, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("BANK", paymentDataWithEmptyreferenceCode, this.order);
        });
    }

    @Test
    void testCreatePaymentWithoutOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("VOUCHER", this.voucherPaymentData, null);
        });
    }

    @Test
    void testCreatePaymentWithInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("MEOW", this.voucherPaymentData, this.order);
        });
    }


    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = new Payment("VOUCHER", this.voucherPaymentData, this.order);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("MEOW");
        });
    }

    @Test
        void testCreateVoucherPaymentSuccess() {
        Payment payment = new Payment("VOUCHER", this.voucherPaymentData, this.order);
        assertNotNull(payment.getId(), "id for payment cant be null");
        assertEquals("SUCCESS", payment.getStatus());
        assertSame(this.voucherPaymentData, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());
    }

    @Test
    void testCreateCODPaymentSuccess() {
        Payment payment = new Payment("BANK", this.bankPaymentData, this.order);
        assertNotNull(payment.getId(), "id for payment cant be null");
        assertEquals("SUCCESS", payment.getStatus());
        assertSame(this.bankPaymentData, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());
    }
}
