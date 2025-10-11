package ru.nsu.sidorenko;


import java.util.Scanner;

/**
 * Класс с реализацией самой игры.
 */
public class BlackJack {

    Deck deck = new Deck();
    Participant player = new Participant();
    Participant dealer = new Participant();
    Scanner scanner = new Scanner(System.in);
    boolean dealerShowAll = false;
    boolean playerTurn = true;

    private int round = 1;
    private int playerwins = 0;
    private int dealerwins = 0;

    /**
     * Показывает карты на руках участников.
     */
    public void showCards() {
        System.out.println("Ваши карты: ");
        player.showHand(true);
        System.out.println("Карты Дилера: ");
        dealer.showHand(dealerShowAll);
    }

    /**
     * Основной метод, в котором реализован игровой процесс. Раздаются карты, затем по очереди
     * вызываются ходы игрока и Дилера. На каждом этапе производятся необходимые проверки.
     */
    public void game() {
        deck = new Deck();
        System.out.println("Добро пожаловать в Блэкджек!\n");
        System.out.println("Раунд " + round);
        System.out.println("Дилер раздал карты\n");
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        showCards();

        if (player.isBlackjack()) {
            if (endGame(1)) {
                game();
            }
        }

        System.out.println("Ваш ход\n");
        System.out.println("-------\n");

        while (playerTurn && player.getScore() < 21) {
            System.out.println("Введите 1, чтобы взять карту, или 0, чтобы остановиться: ");
            int choice = scanner.nextInt();

            playerMove(choice);
            if (player.isLoss()) {
                if (endGame(2)) {
                    game();
                }
            }
        }

        System.out.println("\nХод Дилера\n");
        System.out.println("-------\n");

        dealerMove();
        if (dealer.isLoss()) {
            if (endGame(1)) {
                game();
            }
        }
        else {
            winnerCheck();
        }

    }

    /**
     * Проверка победителя, если ни у кого не оказалось Блекджека или перебора.
     */
    public void winnerCheck() {
        if (dealer.getScore() < player.getScore()) {
            if (endGame(1)) {
                game();
            }
        }
        else if (dealer.getScore() == player.getScore()) {
            if (endGame(1)) {
                game();
            }
        }
        else if (dealer.getScore() > player.getScore()) {
            if (endGame(2)) {
                game();
            }
        }
    }

    /**
     * Ход игрока.
     *
     * @param choice - выбор игрока: брать карту или остановиться
     */
    public void playerMove(int choice) {
        if (choice == 1) {
            Card card = deck.drawCard();
            player.addCard(card);
            System.out.println("Вы открыли карту " + card + "\n");
            showCards();
        }
        else if (choice == 0) {
            playerTurn = false;
        }
        else {
            System.out.println("Некорректный ввод. Введите 1 или 0.");
        }
    }

    /**
     * Ход Дилера. Ходит до того, как наберет 17 или более очков.
     */
    public void dealerMove() {

        if (!dealerShowAll) {
            dealerShowAll = true;
            System.out.println("Дилер открывает закрытую карту " + dealer.getSecondCard() + "\n");
            showCards();
            if (dealer.isBlackjack()) {
                if (endGame(2)) {
                    game();
                }
            }
        }

        while (dealer.getScore() < 17) {
            Card card = deck.drawCard();
            dealer.addCard(card);
            System.out.println("Дилер открывает карту " + card + "\n");
            showCards();
        }

        dealerShowAll = true;
    }

    /**
     * Метод для завершения игры.
     *
     * @param winner - победитель, которому стоит засчитать очко
     * @return вернет true или false, что обозначит, хочет ли игрок сыграть еще один раунд
     */
    public boolean endGame(int winner) {
        if (winner == 1) {
            playerwins++;
            System.out.println("Вы выиграли раунд! Счет " + playerwins + ":" + dealerwins);
            if (playerwins > dealerwins) {
                System.out.println(" В Вашу пользу\n");
            }
            else {
                System.out.println(" В пользу Дилера\n");
            }
        }
        else if (winner == 2) {
            dealerwins++;
            System.out.println("Дилер выиграл раунд! Счет " + playerwins + ":" + dealerwins);
            if (playerwins > dealerwins) {
                System.out.println("В Вашу пользу\n");
            }
            else {
                System.out.println("В пользу Дилера\n");
            }
        }
        round++;
        System.out.println("Введите 1, чтобы продолжить, или что-нибудь другое, чтобы закончить игру: ");
        int choice = scanner.nextInt();
        if (choice == 1) {
            playerTurn = true;
            dealerShowAll = false;
            player.clearHand();
            dealer.clearHand();
            System.out.println("\n======================================\n");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Метод для тестов, чтобы установить конкретную колоду.
     *
     * @param deck устанавливаемая колода
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

}
