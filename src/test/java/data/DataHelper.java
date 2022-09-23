package data;


import lombok.Value;



public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String owner;
        private String cardCVC;
    }

    public static CardInfo giveValidDataOfTheApprovedCard() {
        return new CardInfo("1111 2222 3333 4444", "10", "25", "Ivanov Ivan", "123");
    }

    public static CardInfo giveValidDataOfTheDeclinedCard() {
        return new CardInfo("5555 6666 7777 8888", "10", "25", "Ivanov Ivan", "123");
    }

    public static CardInfo giveInvalidDataOfTheApprovedCard() {
        return new CardInfo("1111 2222 3333 4444", "00", "25", "123ИВан+-/111111111111111111111111111111111111111111111111111*", "000");
    }

    public static CardInfo giveInvalidDataOfTheDeclinedCard() {
        return new CardInfo("5555 6666 7777 8888", "00", "25", "123ИВан+-/111111111111111111111111111111111111111111111111111*", "000");
    }

    public static CardInfo giveAnyCardNumber() {
        return new CardInfo("1111 1111 1111 1111", "10", "25", "Ivanov Ivan", "123");
    }
    public static CardInfo giveEmptyFields() {
        return new CardInfo("", "", "", "", "");
    }
}
