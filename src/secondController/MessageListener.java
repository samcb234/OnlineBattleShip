package secondController;

import java.util.HashMap;

public class MessageListener {

  private HashMap<String, Runnable> command;

  public MessageListener() {
    this.command = new HashMap<>();
  }

  public void addCommand(String s, Runnable r) {
    command.put(s, r);
  }

  public void runCommand(String s) {
    command.get(s).run();
  }
}

