package VulnusProj;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class VulnusCon {
    
    public static void main(String[] args) throws IOException
    {
        String audioID = JOptionPane.showInputDialog(null, "Enter audio ID:");
        String mapData = JOptionPane.showInputDialog(null, "Enter map data:");
        String outputFile = JOptionPane.showInputDialog(null, "Enter output file name:");
        ArrayList<String> dataPoints = new ArrayList<>(0);

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

        /* for(int i = 0; i<dataPoints.size(); i++)
        {
            System.out.print(dataPoints.get(i));

            if((i+1)%3 == 0)
            {
                System.out.print(",");
            }
            else
            {
                System.out.print("|");
            }
        } */

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));
            output.write(audioID + ",");
            for(int i = 0; i<dataPoints.size(); i++)
        {
            if(Double.parseDouble(dataPoints.get(i)) == 0 || Double.parseDouble(dataPoints.get(i)) == 1 || Double.parseDouble(dataPoints.get(i)) == 2 || Double.parseDouble(dataPoints.get(i)) > 2.5)
            {
                dataPoints.set(i, String.format("%.0f",Double.parseDouble(dataPoints.get(i))));
                output.write(dataPoints.get(i));
                //output.write((int) (Math.floor(Double.parseDouble(dataPoints.get(i)))));
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