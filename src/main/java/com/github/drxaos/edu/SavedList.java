package com.github.drxaos.edu;

import java.io.File;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {

   private ArrayList list;
   private File file;

    public SavedList(File file) {
        this.list=new ArrayList();

        if (file==null)
            this.file = new File("savedList.dat");
        else
        {
            this.file = file;
            reload();
        }
    }

    public void reload() throws FileOperationException {
        this.list.clear();

        try{
            FileInputStream streamFile = new FileInputStream(this.file);
            ObjectInputStream streamObj =new ObjectInputStream(streamFile);
            list = (ArrayList)streamObj.readObject();
            streamFile.close();
            streamObj.close();

        }
        catch(Exception ex){}
    }

    private void updFile()
    {
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(list);
            out.close();
        }
        catch (Exception ex){}
    }

    @Override
    public E get(int index) {
        return  (E)list.get(index);
    }

    @Override
    public E set(int index, E element)
    {
        list.set(index, element);
        updFile();
        return this.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {

        list.add(index, element);
        updFile();
    }

    @Override
    public E remove(int index) {
      E obj =  (E)this.list.remove(index);
        updFile();
        return obj;
    }
}

