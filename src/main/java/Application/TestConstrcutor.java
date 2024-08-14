package Application;

import framework.annotation.Autowired;
import framework.annotation.Service;

@Service
public class TestConstrcutor implements ITestConstructor {
    private  ISingleImplementation iSingleImplementation;

    public TestConstrcutor(){}

    @Autowired
    public  TestConstrcutor(ISingleImplementation iSingleImplementation){
        this.iSingleImplementation = iSingleImplementation;
    }
    public  void test(){

        iSingleImplementation.print();
    }

}
