package controller;

import java.util.HashMap;

public class MessageParser {

  private HashMap<String, Runnable> commands;
  private String args;

  public MessageParser(){
    this.commands = new HashMap<>();
  }

  public void addCommand(String s, Runnable r){
    commands.put(s, r);
  }

  public void executeCommand(String s[]) throws IllegalArgumentException{
    if(commands.containsKey(s[0])){
      args = s[2];
      commands.get(s[0]).run();
    }
  }

  public String getArgs(){
    return args;
  }
}
