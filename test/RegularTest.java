import static org.junit.jupiter.api.Assertions.*;

class RegularTest {

    @org.junit.jupiter.api.Test
    void getNum() {
        Regular obj = new Regular("10","Amit", 7000,7);
        assertEquals("10", obj.getNum());
    }

    @org.junit.jupiter.api.Test
    void getName() {
        Regular obj = new Regular("10","Amit", 7000,7);
        assertEquals("Amit", obj.getName());
    }
}