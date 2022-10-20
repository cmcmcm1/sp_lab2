import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
public class Automat {
    List<String> A;  //alphabet
    List<String> S;  //states
    String s0; //first state
    List<String> F;  //final states
    List<List<String>> f;  //functions in format s a s`

    public Automat(Collection<String> A, Collection<String> S, Collection<String> F, List<List<String>> f, String s0){
        this.A=new ArrayList<>();
        this.S=new ArrayList<>();
        this.F=new ArrayList<>();
        this.f=new ArrayList<>();
        this.A.addAll(A);
        this.S.addAll(S);
        this.F.addAll(F);
        this.s0=s0;
        for (int i=0;i<f.size();i++){
            List<String> func=f.get(i);
            this.f.add(func);
        }
    }
    public List<String> getA(){
        return this.A;
    }
    public List<String> getF(){
        return this.F;
    }
    public List<String> getS(){
        return this.S;
    }

    public int getAlphabetCount(){
        return this.A.size();
    }
    public int getStatesCount(){
        return this.S.size();
    }
    public int getFinalCount(){
        return this.F.size();
    }
    public String getS0(){
        return this.s0;
    }
    public List<String> getFunctionsAsString(){
        List<String> functions=new ArrayList<>();
        for(int i=0;i<f.size();i++){
            String func=f.get(i).get(0)+" "+f.get(i).get(1)+" "+f.get(i).get(2);
            functions.add(func);
        }
        return functions;
    }
    public List<List<String>> getFunctionsAsList(){
        return this.f;
    }
}
