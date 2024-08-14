package Application;

import framework.annotation.Service;

public class Samsung implements IProduct {
    @Override
    public void dispaly() {
        System.out.println("------Samsung....");
    }
}
