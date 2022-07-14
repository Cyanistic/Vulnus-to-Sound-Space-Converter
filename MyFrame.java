package Vulnus_Convert_App;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.io.FileWriter;

public class MyFrame extends JFrame implements ActionListener
{
    JButton startButton, saveButton;
    JLabel inLabel, audioLabel, outLabel, fileLabel;
    JTextArea inData, inAudio, conData, outFile;
    String audioID, mapData, outputFile;
    ArrayList<String> dataPoints = new ArrayList<>(0);

    MyFrame()
    {
        this.setVisible(true);
        this.setLayout(null);
        this.setTitle("Vulnus to SS Converter");
        this.setResizable(false);
        JPanel inPanel = new JPanel();
        JPanel outPanel = new JPanel();
        inData = new JTextArea();
        inAudio = new JTextArea();
        inData = new JTextArea();
        inAudio = new JTextArea();
        conData = new JTextArea();
        outFile = new JTextArea();
        inLabel = new JLabel("Vulnus Data:");
        audioLabel = new JLabel("Audio ID:");
        outLabel = new JLabel("Output:");
        fileLabel = new JLabel("Save as:");
        conData.setEditable(false);
        
        startButton = new JButton("Submit");
        startButton.addActionListener(this);
        saveButton = new JButton("Save File");
        saveButton.addActionListener(this);


        inPanel.setBounds(0, 0, 225, 300);
        inPanel.setBackground(Color.GRAY);
        inPanel.setLayout(new BoxLayout(inPanel, BoxLayout.Y_AXIS));
        inPanel.setSize(225, 300);
        outPanel.setBounds(235, 0, 225, 300);
        outPanel.setBackground(Color.GRAY);
        outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.Y_AXIS));
        outPanel.setSize(225, 300);
        inData.setPreferredSize(new Dimension(200, 50));
        inAudio.setPreferredSize(new Dimension(200, 50));
        conData.setPreferredSize(new Dimension(200, 50));
        outFile.setPreferredSize(new Dimension(200, 50));

        inData.setLineWrap(true);
        inAudio.setLineWrap(true);
        conData.setLineWrap(true);
        outFile.setLineWrap(true);

        inPanel.add(inLabel);
        inPanel.add(inData);
        inPanel.add(audioLabel);
        inPanel.add(inAudio);
        inPanel.add(startButton);

        outPanel.add(outLabel);
        outPanel.add(conData);
        outPanel.add(fileLabel);
        outPanel.add(outFile);
        outPanel.add(saveButton);


        this.add(inPanel);
        this.add(outPanel);
        this.setSize(475,339);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startButton)
        {
            dataPoints.clear();
             mapData = inData.getText();
             audioID = inAudio.getText();
             startConvert();
             conData.setText(outputFile);
             System.out.print("\n" + dataPoints.get(0) +"," + dataPoints.get(1) + "," + dataPoints.get(2));
        }
        if(e.getSource()==saveButton && (!outFile.getText().equals("")))
        {

            outputFile = outFile.getText();
            if(!(outputFile.contains(".txt")))
                outputFile = outputFile + ".txt";
             saveData();
        }

    }
    
    public void startConvert()
    {
        
        int tIndex = mapData.indexOf("_time");
        int xIndex = mapData.indexOf("_x");
        int yIndex = mapData.indexOf("_y");
        int miliTime;

        while (yIndex < mapData.lastIndexOf("_y"))
        {
            dataPoints.add(Double.toString(Double.parseDouble((mapData.substring(xIndex+4, mapData.indexOf(",",xIndex))).trim())+1));
            dataPoints.add(Double.toString(Double.parseDouble((mapData.substring(yIndex+4, mapData.indexOf("}",yIndex))).trim())+1));
            miliTime = (int)(Double.parseDouble((mapData.substring(tIndex+7, mapData.indexOf(",",tIndex))).trim())*1000);
            dataPoints.add(Integer.toString(miliTime));

            tIndex = mapData.indexOf("_time", tIndex+5);
            xIndex = mapData.indexOf("_x", xIndex+5);
            yIndex = mapData.indexOf("_y", yIndex+5);
        }

        outputFile = (audioID + ",");
        for(int i = 0; i<dataPoints.size(); i++)
    {
        if(Double.parseDouble(dataPoints.get(i)) == 0 || Double.parseDouble(dataPoints.get(i)) == 1 || Double.parseDouble(dataPoints.get(i)) == 2 || Double.parseDouble(dataPoints.get(i)) > 2.5)
        {
            dataPoints.set(i, String.format("%.0f",Double.parseDouble(dataPoints.get(i))));
            outputFile = outputFile + (dataPoints.get(i));

        }
        else
        {
        dataPoints.set(i, String.format("%.2f",Double.parseDouble(dataPoints.get(i))));
        outputFile = outputFile +(dataPoints.get(i));
        }
        if((i+1)%3 == 0)
        {
            outputFile = outputFile + (",");
        }
        else
        {
            outputFile = outputFile + ("|");
        }
    }

    }

    public void saveData()
    {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));
            output.write(audioID + ",");
            for(int i = 0; i<dataPoints.size(); i++)
        {
            if(Double.parseDouble(dataPoints.get(i)) == 0 || Double.parseDouble(dataPoints.get(i)) == 1 || Double.parseDouble(dataPoints.get(i)) == 2 || Double.parseDouble(dataPoints.get(i)) > 2.5)
            {
                dataPoints.set(i, String.format("%.0f",Double.parseDouble(dataPoints.get(i))));
                output.write(dataPoints.get(i));
                
            }
            else
            {
            dataPoints.set(i, String.format("%.2f",Double.parseDouble(dataPoints.get(i))));
            output.write(dataPoints.get(i));
            }
            if((i+1)%3 == 0)
            {
                output.write(",");
            }
            else
            {
                output.write("|");
            }
        }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
