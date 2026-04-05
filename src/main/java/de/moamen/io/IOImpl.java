package de.moamen.io;

import de.moamen.exceptions.LoadingFailedException;
import de.moamen.exceptions.NoBookFoundException;
import de.moamen.model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOImpl implements IOInterface{
    public static final String BASIS_DIR=new File("").getAbsolutePath()+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"library";
    @Override
    public void save(Book book) {
        File dir=new File(BASIS_DIR);
        if (!dir.exists()){
            dir.mkdir();
        }
        String filePath=BASIS_DIR+File.separator+book.getTitle()+".ser";
        File file=new File(filePath);
        try(FileOutputStream outputStream=new FileOutputStream(file); ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream)){
            objectOutputStream.writeObject(book);
            System.out.println("Book saved "+filePath);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> load() throws NoBookFoundException, LoadingFailedException {
        List<Book> bookList=new ArrayList<>();
        File dir=new File(BASIS_DIR);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File[] files = dir.listFiles(file -> file.getName().endsWith(".ser"));
        if (files != null && files.length == 0) {
            throw new NoBookFoundException("there is no saved books");
        }
        if (files != null) {
            for (File file:files){
                try(FileInputStream inputStream=new FileInputStream(file);ObjectInputStream objectInputStream=new ObjectInputStream(inputStream)){
                    Book book=(Book) objectInputStream.readObject();
                    bookList.add(book);
                }catch (IOException | ClassNotFoundException exception){
                    throw new LoadingFailedException("loading failed");
                }
            }
        }
        return bookList;
    }
}
