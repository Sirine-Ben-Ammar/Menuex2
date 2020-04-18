/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appclasse;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

public class Main extends JFrame {

  private Action openAction = new OpenAction();

  private Action saveAction = new SaveAction();

  private JTextComponent textComp;

  private Hashtable actionHash = new Hashtable();

  public static void main(String[] args) {
    Main editor = new Main();
    editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    editor.setVisible(true);
  }

 
  public Main() {
    super("Swing Editor");
    textComp = createTextComponent();
    makeActionsPretty();

    Container content = getContentPane();
    content.add(textComp, BorderLayout.CENTER);
    content.add(createToolBar(), BorderLayout.NORTH);
    setJMenuBar(createMenuBar());
    setSize(320, 240);
  }


  protected JTextComponent createTextComponent() {
    JTextArea ta = new JTextArea();
    ta.setLineWrap(true);
    return ta;
  }

 

  
  protected JToolBar createToolBar() {
    JToolBar bar = new JToolBar();

    

    bar.add(getOpenAction()).setText("");
    bar.add(getSaveAction()).setText("");
    bar.addSeparator();

   
    bar.add(textComp.getActionMap().get(DefaultEditorKit.cutAction))
        .setText("");
    bar.add(textComp.getActionMap().get(DefaultEditorKit.copyAction))
        .setText("");
    bar.add(textComp.getActionMap().get(DefaultEditorKit.pasteAction))
        .setText("");
    return bar;
  }

  
  protected JMenuBar createMenuBar() {
    JMenuBar menubar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");
    menubar.add(file);
    menubar.add(edit);

    file.add(getOpenAction());
    file.add(getSaveAction());
    file.add(new ExitAction());
    edit.add(textComp.getActionMap().get(DefaultEditorKit.cutAction));
    edit.add(textComp.getActionMap().get(DefaultEditorKit.copyAction));
    edit.add(textComp.getActionMap().get(DefaultEditorKit.pasteAction));
    edit.add(textComp.getActionMap().get(DefaultEditorKit.selectAllAction));
    return menubar;
  }

  
  protected void makeActionsPretty() {
    Action a;
    a = textComp.getActionMap().get(DefaultEditorKit.cutAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("cut.gif"));
    a.putValue(Action.NAME, "Cut");

    a = textComp.getActionMap().get(DefaultEditorKit.copyAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("copy.gif"));
    a.putValue(Action.NAME, "Copy");

    a = textComp.getActionMap().get(DefaultEditorKit.pasteAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("paste.gif"));
    a.putValue(Action.NAME, "Paste");

    a = textComp.getActionMap().get(DefaultEditorKit.selectAllAction);
    a.putValue(Action.NAME, "Select All");
  }
  protected Action getOpenAction() {
    return openAction;
  }

 
  protected Action getSaveAction() {
    return saveAction;
  }

  protected JTextComponent getTextComponent() {
    return textComp;
  }

 
  public class ExitAction extends AbstractAction {
    public ExitAction() {
      super("Exit");
    }

    public void actionPerformed(ActionEvent ev) {
      System.exit(0);
    }
  }

 
  class OpenAction extends AbstractAction {
    public OpenAction() {
      super("Open", new ImageIcon("icons/open.gif"));
    }

    public void actionPerformed(ActionEvent ev) {
      JFileChooser chooser = new JFileChooser();
      if (chooser.showOpenDialog(Main.this) != JFileChooser.APPROVE_OPTION)
        return;
      File file = chooser.getSelectedFile();
      if (file == null)
        return;

      FileReader reader = null;
      try {
        reader = new FileReader(file);
        textComp.read(reader, null);
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(Main.this,
            "File Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
      } finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException x) {
          }
        }
      }
    }
  }

  
  class SaveAction extends AbstractAction {
    public SaveAction() {
      super("Save", new ImageIcon("icons/save.gif"));
    }

   
    public void actionPerformed(ActionEvent ev) {
      JFileChooser chooser = new JFileChooser();
      if (chooser.showSaveDialog(Main.this) != JFileChooser.APPROVE_OPTION)
        return;
      File file = chooser.getSelectedFile();
      if (file == null)
        return;

      FileWriter writer = null;
      try {
        writer = new FileWriter(file);
        textComp.write(writer);
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(Main.this,
            "File Not Saved", "ERROR", JOptionPane.ERROR_MESSAGE);
      } finally {
        if (writer != null) {
          try {
            writer.close();
          } catch (IOException x) {
          }
        }
      }
    }
  }
}


