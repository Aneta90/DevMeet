package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

class MessengerCrudUpdater {

    private MessengerRepository messengerRepository;

    public MessengerCrudUpdater(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }
}