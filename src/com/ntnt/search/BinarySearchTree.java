package com.ntnt.search;

import com.ntnt.annotations.IsCounted;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class BinarySearchTree<T extends Comparable<T>> {
    public static enum ShowType {
        NLR, LNR, LRN;
    }

    private T data;

    private BinarySearchTree left;
    private BinarySearchTree right;
    private static Logger logger = Logger.getLogger(BinarySearchTree.class.getName());

    //    Constructor
    public BinarySearchTree() {
        this.data = null;
    }

    public BinarySearchTree(T data) {
        this.data = data;
        if (isExistAnnotation(IsCounted.class)) {
            this.setCount(1);
        }
    }

    // private functions
    private boolean isExistAnnotation(Class annotationClass) {
        return (this.data.getClass().getAnnotation(annotationClass) == null) ? false : true;
    }

    private int getCount(){
        try{
            IsCounted countField = this.data.getClass().getAnnotation(IsCounted.class);
            String fieldName = (countField == null) ? "count" : countField.name();
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
            String methodName = "get" + fieldName;
            Method method = this.data.getClass().getDeclaredMethod(methodName);

            return (int) method.invoke(this.data);
        }catch (Exception e){

        }
        return 0;
    }

    private void setCount(int count) {
        try{
            IsCounted countField = this.data.getClass().getAnnotation(IsCounted.class);
            String fieldName = (countField == null) ? "count" : countField.name();
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
            String methodName = "set" + fieldName;
            Method method = this.data.getClass().getDeclaredMethod(methodName, int.class);

            method.invoke(this.data, count);
        }catch (Exception e){

        }
    }

    //  functions
    public BinarySearchTree add(T data) {
        if (this.data == null) {
            this.data = data;
        } else if (this.data.compareTo(data) > 0) {
            if (this.right == null)
                this.right = new BinarySearchTree(data);
            else
                this.right.add(data);
        } else if (this.data.compareTo(data) < 0) {
            if (this.left == null)
                this.left = new BinarySearchTree(data);
            else
                this.left.add(data);
        } else {
            this.setCount(this.getCount() + 1);
        }
        return this;
    }

    public T search(T data){
        BinarySearchTree node = this;

        while(node != null){
            int compareValue = node.getData().compareTo(data);
            if(compareValue == 0)
                return (T) node.getData();
            else if(compareValue < 0){
                node = node.getLeft();
            }else{
                node = node.getRight();
            }
        }
        return null;
    }

    public void show() {
        this.show(ShowType.NLR);
    }

    public void show(ShowType type) {
        System.out.println("Data:");
        switch (type) {
            case NLR:
                this.showNLR();
                break;
            case LNR:
                this.showLNR();
                break;
            case LRN:
                this.showLRN();
        }
    }

    public void showNLR() {
        System.out.println(this.data.toString());
        if (this.left != null)
            this.left.showNLR();
        if (this.right != null)
            this.right.showNLR();
    }

    public void showLNR() {
        if (this.left != null)
            this.left.showNLR();

        System.out.println(this.data.toString());

        if (this.right != null)
            this.right.showNLR();
    }

    public void showLRN() {
        if (this.left != null)
            this.left.showNLR();

        if (this.right != null)
            this.right.showNLR();

        System.out.println(this.data.toString());
    }

//    Getter & Setter

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BinarySearchTree getLeft() {
        return left;
    }

    public void setLeft(BinarySearchTree left) {
        this.left = left;
    }

    public BinarySearchTree getRight() {
        return right;
    }

    public void setRight(BinarySearchTree right) {
        this.right = right;
    }
}
