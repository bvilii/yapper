package io.github.bvilii.yapper.client.ui;

public class ClientDisplay {
    private static final String RESET = "\u001B[0m";
    private static final String[] COLORS = { "\u001B[31m", "\u001B[32m",
            "\u001B[33m", "\u001B[34m"
    };

    public void displayChatMessage(String sender, String content) {
        String color = colorFor(sender);
        System.out.println(color + "<" + sender + "> " + RESET + content);
    }

    public void displayInfo(String info) {
        System.out.println("[" + info + "]");
    }

    private static String colorFor(String username){
        int index = Math.abs(username.hashCode()) % COLORS.length;
        return COLORS[index];
    }
}
