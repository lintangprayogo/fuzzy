package com.company;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Fuzzy {
    private ArrayList<InfluencerInput> influencersCrips = new ArrayList<>();
    private ArrayList<InfluencerFuzzyInput> influencersFuzzy = new ArrayList<>();
    private ArrayList<InfluencerInference> influencersInferences = new ArrayList<>();
    private ArrayList<InfluencerOutput> influencerOutputs=new ArrayList<>();
    private static  final  double REJECECTED_CONST =50,consideredConst=70,acceptConst=100;
    private static  final String BASE_PATH= new File("").getAbsolutePath();


    public void  read(String path ){
        String row;
        int  count=0;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(BASE_PATH+"/"+path));

            while ((row = csvReader.readLine())!=null){
                if(count!=0){
                    String data[] = row.split(",");
                    String id=data[0];
                    int  folowerCount= Integer.parseInt(data[1]);
                    double engagementRate= Double.parseDouble(data[2]);
                    influencersCrips.add(new InfluencerInput(id,folowerCount,engagementRate));
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void fuzzification(){
        for (InfluencerInput data: influencersCrips) {
            InfluencerFuzzyInput dataFuzzy = new InfluencerFuzzyInput();
            dataFuzzy.setId(data.getId());

            double bottom=0;
            if(data.getFolowerCount()<=10000){
                bottom=1;
            }else if(data.getFolowerCount()>10000&&data.getFolowerCount()<=15000){
                bottom= (15000-data.getFolowerCount())/(15000-10000);
            }
            dataFuzzy.getFolowerCountClusters().add(new Cluster("Bottom",bottom));

            double mediocre=0;
            if(data.getFolowerCount()>20000 && data.getFolowerCount()<=25000){
                mediocre=1;
            }else if(data.getFolowerCount()>10000 && data.getFolowerCount()<=20000){
                mediocre= (data.getFolowerCount()-10000)/(20000-10000);
            }else if(data.getFolowerCount()>25000 && data.getFolowerCount()<=30000){
                mediocre = - (data.getFolowerCount()-30000)/(30000-25000);
            }

            dataFuzzy.getFolowerCountClusters().add(new Cluster("Mediocre",mediocre));

            double upper=0;
            if(data.getFolowerCount()>35000){
                upper=1;
            }else if(data.getFolowerCount()>25000 && data.getFolowerCount()<=35000){
                upper = (data.getFolowerCount()-25000)/(35000-25000);
            }

            dataFuzzy.getFolowerCountClusters().add(new Cluster("Upper",upper));


            double weak = 0;

            if(data.getEngagementRate()<=2.5){
                weak=1;
            }else if(data.getEngagementRate()>2.5 && data.getEngagementRate() <=3.0){
                weak= (3.0-data.getEngagementRate())/(3.0-2.5);
            }
            dataFuzzy.getEngagementRateClusters().add(new Cluster("Weak",weak));

            double  medium=0;
            if(data.getEngagementRate()>3.5 && data.getEngagementRate()<=4.5){
                medium=1;
            }else if(data.getEngagementRate()>2.5 && data.getEngagementRate()<=3.5){
                medium =(data.getEngagementRate()-2.5)/(3.5-2.5);
            } else if  (data.getEngagementRate()>4.5 && data.getEngagementRate()<=5.0){
                medium =(5.0-data.getEngagementRate())/(3.5-2.5);
            }

            dataFuzzy.getEngagementRateClusters().add(new Cluster("Medium",medium));

            double strong=0;
            if(data.getEngagementRate()>6){
                strong=1;
            }else if(data.getEngagementRate()>=4.5 && data.getEngagementRate()<=6){
                strong = (data.getEngagementRate()-4.5)/(6-4.5);
            }
            dataFuzzy.getEngagementRateClusters().add(new Cluster("Strong",strong));
            influencersFuzzy.add(dataFuzzy);
        }
    }


    public  void  Infrence(){
        for (InfluencerFuzzyInput data: influencersFuzzy) {
            Cluster bottom = data.getFolowerCountClusters().get(0);
            Cluster mediocre = data.getFolowerCountClusters().get(1);
            Cluster upper = data.getFolowerCountClusters().get(2);
            Cluster weak = data.getEngagementRateClusters().get(0);
            Cluster medium = data.getEngagementRateClusters().get(1);
            Cluster strong = data.getEngagementRateClusters().get(2);
            InfluencerInference inference = new InfluencerInference();
            inference.setId(data.getId());
            //Upper  And Strong
            if(upper.getValue() > strong.getValue()){
                inference.getScoreClusters().add(new Cluster("Accept",strong.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Upper",upper.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Strong",strong.getValue()));
            }else {
                inference.getScoreClusters().add(new Cluster("Accept",upper.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Upper",upper.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Strong",strong.getValue()));
            }



            //Upper  And Medium
            if(upper.getValue() > medium.getValue()){
                inference.getScoreClusters().add(new Cluster("Accept",medium.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Upper",upper.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Medium",medium.getValue()));
            }else {
                inference.getScoreClusters().add(new Cluster("Accept",upper.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Upper",upper.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Medium",medium.getValue()));
            }
            //Upper  And Weak
            if(upper.getValue() > weak.getValue()){
                inference.getScoreClusters().add(new Cluster("Considered",weak.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Upper",upper.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Weak",weak.getValue()));
            }else {
                inference.getScoreClusters().add(new Cluster("Considered",upper.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Upper",upper.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Weak",weak.getValue()));
            }


            //Mediocre and Strong
            if(mediocre.getValue() > strong.getValue()){
                inference.getScoreClusters().add(new Cluster("Accept",strong.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Mediocre", mediocre.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Strong",strong.getValue()));
            }else {
                inference.getScoreClusters().add(new Cluster("Accept",mediocre.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Mediocre",mediocre.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Strong",strong.getValue()));
            }

            //Mediocre and Medium
            if(mediocre.getValue() > medium.getValue()){
                inference.getScoreClusters().add(new Cluster("Considered",medium.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Mediocre", mediocre.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Medium",medium.getValue()));
            }else {
                inference.getScoreClusters().add(new Cluster("Considered",mediocre.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Mediocre",mediocre.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Medium",medium.getValue()));
            }


            //Mediocre and Weak
            if(mediocre.getValue() > weak.getValue()){
                inference.getScoreClusters().add(new Cluster("Reject",weak.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Mediocre", mediocre.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Weak",weak.getValue()));
            }else {
                inference.getScoreClusters().add(new Cluster("Reject",mediocre.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Mediocre",mediocre.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Weak",weak.getValue()));
            }


            //Bottom and Strong
            if(bottom.getValue() > strong.getValue()){
                inference.getScoreClusters().add(new Cluster("Considered",strong.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Bottom", bottom.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Strong",strong.getValue()));
            }else {
                inference.getScoreClusters().add(new Cluster("Considered",bottom.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Bottom",bottom.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Strong",strong.getValue()));
            }

            //Bottom and Medium
            if(bottom.getValue() > medium.getValue()){
                inference.getScoreClusters().add(new Cluster("Reject",medium.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Bottom", bottom.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Medium",medium.getValue()));
            }else {
                inference.getScoreClusters().add(new Cluster("Reject",bottom.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Bottom",bottom.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Medium",medium.getValue()));
            }


            //Bottom and Weak
            if(bottom.getValue() > weak.getValue()){
                inference.getScoreClusters().add(new Cluster("Reject",weak.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Bottom", bottom.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Weak",weak.getValue()));
            }else {
                inference.getScoreClusters().add(new Cluster("Reject",bottom.getValue()));
                inference.getFolowerCountClusters().add(new Cluster("Bottom",bottom.getValue()));
                inference.getEngagementRateClusters().add(new Cluster("Weak",weak.getValue()));
            }

            influencersInferences.add(inference);

        /*    int count=0;
            for (Cluster cluster:inference.getScoreClusters()) {
                System.out.println(inference.getFolowerCountClusters().get(count).toString()+","+inference.getEngagementRateClusters().get(count)+","+inference.getScoreClusters().get(count));
              count++;
            }
            System.out.println("==============================================================");*/

        }



    }



    public  void diFuzzyfication(){
        for (InfluencerInference data:influencersInferences) {
            double reject=0;
            double considered=0;
            double accept=0;
            for (Cluster cluster:data.getScoreClusters()) {
                if(reject<cluster.getValue() && cluster.getLabel().equals("Reject")){
                    reject=cluster.getValue();
                }
                else if(considered<cluster.getValue() && cluster.getLabel().equals("Considered")){
                    considered=cluster.getValue();
                } else if(accept<cluster.getValue() && cluster.getLabel().equals("Accept")){
                    accept=cluster.getValue();
                }
            }

            double result=    ((reject* REJECECTED_CONST)+(considered*consideredConst)+(accept*acceptConst))/(reject+considered+accept);
            influencerOutputs.add(new InfluencerOutput(data.getId(),result));

        }


        influencerOutputs.sort((a, b) -> Double.compare(b.getResult(),a.getResult()));
        writeDataLineByLine();
    }

    public  void writeDataLineByLine() {
             try {

                 FileWriter csvWriter = new FileWriter("Choosen.csv");
                 for (int i = 0; i <=19 ; i++) {
                     csvWriter.append(influencerOutputs.get(i).toString());
                     csvWriter.append("\n");
                 }

                 csvWriter.flush();
                 csvWriter.close();
             }catch (IOException e){
                 e.printStackTrace();
             }
        File currentDirFile = new File("Choosen.csv");

        System.out.println("Silahkan Buka  File di  = "+currentDirFile.getAbsolutePath());
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(currentDirFile.getAbsolutePath());
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }



}
