package Application;

import framework.annotation.Service;

@Service
public class SingleImplementation implements ISingleImplementation {
    @Override
    public void print() {
        System.out.println("Testing SIngle...");
    }
}
