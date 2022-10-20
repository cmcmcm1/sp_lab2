import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args){
        Automat inputAutomate=getInputAutomate();
        Automat outputAutomate=getOutputAutomate(inputAutomate);
        writeAutomate(outputAutomate);
    }
    public static void writeAutomate(Automat automat){
        File file=new File("output.txt");
        try{
            FileWriter writer=new FileWriter(file);
            writer.write(Integer.toString(automat.getAlphabetCount()) + '\n');
            writer.write(Integer.toString(automat.getStatesCount()) + '\n');
            writer.write(automat.getS0() + '\n');
            writer.write(Integer.toString(automat.getFinalCount()) + " ");
            writer.write(ListToString(automat.getF()) + '\n');
            List<List<String>> f=automat.getFunctionsAsList();
            for(int i=0;i<f.size();i++){
                writer.write(ListToString(f.get(i)) + '\n');
            }
            writer.close();
        }
        catch (IOException exception){
            System.out.println("something went wrong");
            throw new RuntimeException(exception);
        }
    }
    public static Automat getOutputAutomate(Automat inputAutomate){
        List<String> alphabet=inputAutomate.getA();
        List<String> States = inputAutomate.getS();
        List<List<String>> f = inputAutomate.getFunctionsAsList();
        List<String> finals=inputAutomate.getF();

        String s0=inputAutomate.getS0();

        List<List<String>> newStates =new ArrayList<>(); //list for new states
        List<List<String>> newFunctions=new ArrayList<>(); //list for new functions
        List<List<String>>newFinals=new ArrayList<>(); //list for new finals

        for(int i=0;i<States.size();i++){ //put s0 to start of states list
            if(States.get(i).equals(s0)){
                States.set(i,States.get(0));
                States.set(0,s0);
                break;
            }
        }
        newStates.add(Arrays.asList(States.get(0)));
        int i=0;
        while(i<newStates.size()){
            for(int a=0;a<alphabet.size();a++){
                List<String> state=new ArrayList<>();
                List<String> func=new ArrayList<>();
                for(int k=0;k<f.size();k++){
                    if(newStates.get(i).contains(f.get(k).get(0))){
                        if(f.get(k).get(1).equals(alphabet.get(a))){
                            if(!state.contains(f.get(k).get(2))){
                                state.add(f.get(k).get(2));
                            }
                        }
                    }
                }
                Collections.sort(state);
                if(!newStates.contains(state)){
                    newStates.add(state);
                    for(int fin=0;fin<finals.size();fin++){
                        if(state.contains(finals.get(fin))){
                            if(!newFinals.contains(state)){
                                newFinals.add(state);
                            }
                        }
                    }
                }
                func.add(newStates.get(i).toString());
                func.add(alphabet.get(a));
                func.add(state.toString());
                newFunctions.add(func);
            }
            i++;
        }
        return new Automat(alphabet, ListOfListToListOfString(newStates),ListOfListToListOfString(newFinals),newFunctions,s0);
    }
    public static String ListAsString(List<String> L){
        String s=L.stream().collect(Collectors.joining(", ","[","]"));
        return s;
    }
    public static String ListToString(List<String> L){
        String s=L.stream().collect(Collectors.joining(" "));
        return s;
    }
    public static List<String>ListOfListToListOfString (List<List<String>> L){
        List<String> newL=new ArrayList<>();
        for(int i=0;i<L.size();i++){
            newL.add(ListAsString(L.get(i)));
        }
        return newL;
    }
    public static Automat getInputAutomate(){  //function to get automate from input file
        File file = new File("input.txt");
        Scanner s= null;
        try{
            s=new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            throw new RuntimeException(e);
        }
        String line=s.nextLine();
        line=s.nextLine();
        line=s.nextLine();
        String s0=line;
        line=s.nextLine();
        String[] l=line.split(" ");
        int finalsCount=Integer.parseInt(l[0]);
        HashSet<String> F=new HashSet<>();
        HashSet<String> A=new HashSet<>();
        HashSet<String> S=new HashSet<>();
        List<List<String>> f=new ArrayList<>();
        for(int i=0;i<finalsCount;i++){
            F.add(l[i+1]);
        }
        for(int i=0; s.hasNext(); i++){
            line=s.nextLine();
            List<String> p = Arrays.asList(line.split(" "));
            S.add(p.get(0));
            S.add(p.get(2));
            A.add(p.get(1));
            f.add(p);
        }

        return new Automat(A,S,F,f,s0);
    }



}
