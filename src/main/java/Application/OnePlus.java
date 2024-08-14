package Application;

import framework.annotation.Service;

@Service
public class OnePlus implements IProduct {
    @Override
    public void dispaly() {
        System.out.println("OnePlus-----Haddus Guru");
    }
}
