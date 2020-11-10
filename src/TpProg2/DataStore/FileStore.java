package TpProg2.DataStore;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class FileStore<T extends FileSaveable> implements DataStore<T>{

    String fileName;

    public FileStore(String fileName) {
        this.fileName = "src/TpProg2/DataStore/data/" + fileName;
    }

    @Override
    public void save(T t) {
        if (!exists(t.getId())) {
            try {
                FileWriter fileWriter = new FileWriter(fileName, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.newLine();
                bufferedWriter.write(t.getFileRepresentation());
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Metodo remove line de un file ---> chequealo mas a fondo.
    @Override
    public void remove/*FileLine*/(String id) { /* leer archivo guardar en un arraylist users, borro el user con ese id y lo escribo devuelta.*/
        ArrayList<String> allLines = collectFileLines();
        for(Iterator<String> itr = allLines.iterator(); itr.hasNext();){
            String line = itr.next();
            if(line.startsWith(id)){
                itr.remove();
                cleanFile();
            }

        }
        for (int i = 0; i < allLines.size(); i++) {
            if (allLines.size()-1 > i){
                writeFile(allLines.get(i) + "\n");
            }else {
                writeFile(allLines.get(i));
            }
        }
    }

    public ArrayList<String> collectFileLines(){
        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(this.fileName));){
            String line = br.readLine();
            int i = 0;
            while(line != null){
                lines.add(line);
                line = br.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void writeFile(String toWrite){
        //metodo para abstraer escribir en un archivo.
        try(BufferedWriter br = new BufferedWriter(new FileWriter(this.fileName, true));){
            br.write(toWrite);
        } catch(IOException e){
            e.getMessage();
        }
    }

    private void cleanFile(){
        try {
            PrintWriter writer = new PrintWriter(new File((this.fileName)));
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public T findById(String id) {
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] data = strLine.split(",");
                if (data[0].equals(id)) {
                    return this.lineToObject(strLine);
                }
            }
            fstream.close();
        }catch (IOException e){
            e.getMessage();
        }
        return null;
    }

    public T lineToObject(String line){
        return null;
    }

    @Override
    public void edit(T t) {
        this.remove(t.getId());
        this.save(t);
    }

    @Override
    public boolean isEmpty() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            if (br.readLine() == null) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean exists(String id) {
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] data = strLine.split(",");
                if (data[0].equals(id)) {
                    return true;
                }
            }
            fstream.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<T> toArrayList() {
        return null;
    }
}
