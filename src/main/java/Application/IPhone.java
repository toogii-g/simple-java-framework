package Application;

import framework.annotation.Service;


public class IPhone implements IProduct {
    @Override
    public void dispaly() {
        System.out.println("Iphone------>>");
    }
}
