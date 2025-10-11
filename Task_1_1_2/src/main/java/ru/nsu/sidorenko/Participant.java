package ru.nsu.sidorenko;

public class Participant {
    protected Hand hand = new Hand();

    /**
     * Добавление карты в руку игрока или дилера
     * @param card - карта, которую нужно добавить в руку
     */
    public void addCard(Card card) {
        hand.addCard(card);
    }

    /**
     * Определение суммы очков карт в руке у игрока или дилера
     * @return вернет сумму очков
     */
    public int getScore() {
        return hand.getValue();
    }

    /**
     * Проверка на проигрыш (если сумма очков карт в руке более 21)
     * @return вернет True или False
     */
    public boolean isLoss() {
        return hand.isLoss();
    }

    /**
     * Проверка на блекджек (если сумма очков равна 21)
     * @return вернет True или False
     */
    public boolean isBlackjack() {
        return hand.isBlackjack();
    }

    /**
     * Очистка руки конкретного участника
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Вернет вторую карту из руки участника. Необходим для открытия закрытой карты Дилера
     * @return вернет вторую карту на руке Дилера
     */
    public Card getSecondCard() {
        return hand.getCard(1);
    }

    /**
     * Вывод карт игрока и дилера. Игрок видит все свои карты, а из карт дилера в начале игры только
     * первую карту, вторая для него закрыта
     * @param showAll говорит, нужно ли сейчас показывать всю колоду или только первую карту
     *                (в случае начала игры у дилера)
     */
    public void showHand(boolean showAll) {
        if (showAll) {
            System.out.println("[" + hand + "] ");
            System.out.println("=> " + getScore());
        } else {
            System.out.println("[" + hand.getFirstCard() + ", <закрытая карта>]");
        }
    }
}